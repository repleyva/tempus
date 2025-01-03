package com.repleyva.tempus.presentation.extensions

import android.content.Context
import com.repleyva.tempus.R
import java.util.Calendar
import java.util.TimeZone

fun Context.formatDay(timezoneOffset: Int): String {

    val utcTimeInMillis = System.currentTimeMillis()
    val localTimeInMillis = utcTimeInMillis + (timezoneOffset * 1000L)

    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = localTimeInMillis

    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    return when (hour) {
        in 0..11 -> getString(R.string.weather_greeting_day)
        in 12..17 -> getString(R.string.weather_greeting_afternoon)
        else -> getString(R.string.weather_greeting_evening)
    }
}