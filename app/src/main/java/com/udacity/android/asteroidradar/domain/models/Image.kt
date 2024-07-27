package com.udacity.android.asteroidradar.domain.models

import com.squareup.moshi.Json
import com.udacity.android.asteroidradar.domain.database.DatabaseImage

data class Image(
    val date: String,
    @Json(name = "explanation") val explanation: String,
    @Json(name = "hdurl") val imageUrlHd: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    @Json(name = "url") val imageUrl: String,
    val id: Long = 0
) {
    fun asDatabaseModel(): DatabaseImage {
        return DatabaseImage(
            date = date,
            explanation = explanation,
            imageUrlHd = imageUrlHd,
            media_type = media_type,
            service_version = service_version,
            title = title,
            imageUrl = imageUrl,
            id = id
        )
    }
}



