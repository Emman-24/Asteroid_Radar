package com.udacity.android.asteroidradar.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun getToday(): String {
    val date = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)
    return date.format(formatter)
}