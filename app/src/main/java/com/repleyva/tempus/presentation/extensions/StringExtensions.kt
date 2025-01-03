package com.repleyva.tempus.presentation.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun String.formatDate(): String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        originalFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = originalFormat.parse(this)

        if (date != null) {
            val now = Date()
            val diffInMillis = now.time - date.time

            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
            val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)

            when {
                minutes < 1 -> "Just now"
                minutes == 1L -> "1 minute ago"
                minutes < 60 -> "$minutes minutes ago"
                hours == 1L -> "1 hour ago"
                hours < 24 -> "$hours hours ago"
                else -> {
                    val fullDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    fullDateFormat.format(date)
                }
            }
        } else {
            this
        }
    } catch (e: Exception) {
        this
    }
}