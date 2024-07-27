package com.udacity.android.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.android.asteroidradar.domain.database.getDatabase
import com.udacity.android.asteroidradar.domain.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }


}