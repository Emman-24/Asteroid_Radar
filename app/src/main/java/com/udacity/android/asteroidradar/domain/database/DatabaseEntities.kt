package com.udacity.android.asteroidradar.domain.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseAsteroid(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@Entity
data class DatabaseImage constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String,
    val explanation: String,
    val imageUrlHd: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val imageUrl: String
)



