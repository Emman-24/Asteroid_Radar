package com.udacity.android.asteroidradar.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.android.asteroidradar.api.NasaAsteroidApi
import com.udacity.android.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.android.asteroidradar.domain.database.AsteroidsDatabase
import com.udacity.android.asteroidradar.domain.database.DatabaseAsteroid
import com.udacity.android.asteroidradar.domain.database.DatabaseImage
import com.udacity.android.asteroidradar.domain.models.Asteroid
import com.udacity.android.asteroidradar.utils.getToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class AsteroidRepository(private val database: AsteroidsDatabase) {

    val allAsteroids = database.asteroidDao.getAsteroids().map { asteroidEntities ->
        asteroidEntities.map {
            Asteroid(
                id = it.id,
                codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
            )
        }
    }

    val todayAsterois =
        database.asteroidDao.getAsteroidsByDate(getToday()).map { databaseAsteroids ->
            databaseAsteroids.map {
                Asteroid(
                    id = it.id,
                    codename = it.codename,
                    closeApproachDate = it.closeApproachDate,
                    absoluteMagnitude = it.absoluteMagnitude,
                    estimatedDiameter = it.estimatedDiameter,
                    relativeVelocity = it.relativeVelocity,
                    distanceFromEarth = it.distanceFromEarth,
                    isPotentiallyHazardous = it.isPotentiallyHazardous
                )
            }
        }

    //    val todayAsteroids = database.asteroidDao.getAsteroidsByDate(getToday()).map {
//        it.asDomainModel()
//    }
    val getImage: LiveData<DatabaseImage> = database.imageDao.getImage()


    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val response = NasaAsteroidApi.retrofitService.getAsteroids().await()
            val asteroids: ArrayList<Asteroid> = parseAsteroidsJsonResult(JSONObject(response))
            database.asteroidDao.insertAll(*asteroids.map {
                DatabaseAsteroid(
                    id = it.id,
                    codename = it.codename,
                    closeApproachDate = it.closeApproachDate,
                    absoluteMagnitude = it.absoluteMagnitude,
                    estimatedDiameter = it.estimatedDiameter,
                    relativeVelocity = it.relativeVelocity,
                    distanceFromEarth = it.distanceFromEarth,
                    isPotentiallyHazardous = it.isPotentiallyHazardous
                )
            }.toTypedArray())
        }
    }

    suspend fun getPhotoOfTheDay() {
        withContext(Dispatchers.IO) {
            val response = NasaAsteroidApi.retrofitService.getPictureOfTheDay()
            database.imageDao.insertImage(response.asDatabaseModel())
        }
    }

}





