package com.udacity.android.asteroidradar.ui.imageDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.udacity.android.asteroidradar.domain.database.getDatabase

class ImageDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)

     val photoOfTheDay = database.imageDao.getImage()


}