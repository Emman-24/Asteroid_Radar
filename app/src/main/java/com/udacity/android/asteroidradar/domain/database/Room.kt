package com.udacity.android.asteroidradar.domain.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM DatabaseAsteroid ORDER BY closeApproachDate ASC")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("SELECT * FROM DatabaseAsteroid WHERE closeApproachDate = :date")
    fun getAsteroidsByDate(date: String): LiveData<List<DatabaseAsteroid>>

}

@Dao
interface ImageDao {

    @Query("SELECT * FROM DatabaseImage")
    fun getImage(): LiveData<DatabaseImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = DatabaseImage::class)
    fun insertImage(image: DatabaseImage)
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE IF NOT EXISTS DatabaseImage(
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    date TEXT NOT NULL,
                    explanation TEXT NOT NULL,
                    imageUrlHd TEXT NOT NULL,
                    media_type TEXT NOT NULL,
                    service_version TEXT NOT NULL,
                    title TEXT NOT NULL,
                    imageUrl TEXT NOT NULL
                )
            """.trimIndent()
        )
    }
}

@Database(
    entities = [DatabaseAsteroid::class, DatabaseImage::class],
    version = 2,
)
abstract class AsteroidsDatabase() : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val imageDao: ImageDao
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidsDatabase::class.java,
                "asteroids"
            ).addMigrations(MIGRATION_1_2)
                .build()
        }
    }
    return INSTANCE
}