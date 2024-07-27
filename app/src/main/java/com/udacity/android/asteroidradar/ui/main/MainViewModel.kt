package com.udacity.android.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.android.asteroidradar.domain.database.getDatabase
import com.udacity.android.asteroidradar.domain.models.Asteroid
import com.udacity.android.asteroidradar.domain.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class NasaApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<NasaApiStatus?>()
    val status: LiveData<NasaApiStatus?> = _status

    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment: LiveData<Asteroid?> = _navigateToDetailFragment

    private val _navigateToImageDetailFragment = MutableLiveData<Boolean>()
    val navigateToImageDetailFragment: LiveData<Boolean> = _navigateToImageDetailFragment

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> = _asteroids

    private val _refreshTrigger = MutableLiveData(false)

    init {
        viewModelScope.launch {
            refreshData()
            asteroidRepository.allAsteroids.observeForever {
                _asteroids.value = it
            }
        }
    }


    private fun refreshData() {
        viewModelScope.launch {
            _status.value = NasaApiStatus.LOADING
            try {
                asteroidRepository.refreshAsteroids()
                asteroidRepository.getPhotoOfTheDay()
                _status.value = NasaApiStatus.DONE
                _refreshTrigger.value = true
            } catch (e: Exception) {
                _status.value = NasaApiStatus.ERROR
            }
        }
    }

    val photoOfTheDay = asteroidRepository.getImage

    fun showTodayAsteroids() {
        asteroidRepository.todayAsterois.observeForever {
            _asteroids.value = it
        }

    }

    fun showWeekAsteroids() {
        asteroidRepository.allAsteroids.observeForever {
            _asteroids.value = it
        }
    }

    fun onImageOfTheDayClicked() {
        _navigateToImageDetailFragment.value = true
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetailFragment.value = asteroid
    }

    fun doneNavigationImage() {
        _navigateToImageDetailFragment.value = false
    }

    fun doneNavigating() {
        _navigateToDetailFragment.value = null
    }


}