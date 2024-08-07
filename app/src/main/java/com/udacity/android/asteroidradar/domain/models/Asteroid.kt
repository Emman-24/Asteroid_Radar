package com.udacity.android.asteroidradar.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class NetworkAsteroidContainer(val asteroids: ArrayList<Asteroid>)

@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable