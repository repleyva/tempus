package com.repleyva.tempus.presentation.extensions

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun Int.weatherDate(): String {
    val utcTimeInMillis = System.currentTimeMillis()
    val localTimeInMillis = utcTimeInMillis + (this * 1000L)

    val originalFormat = SimpleDateFormat("EEE dd MMM, yyyy", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")
    return originalFormat.format(localTimeInMillis)
}