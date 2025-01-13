package com.repleyva.tempus.presentation.extensions

import com.repleyva.tempus.domain.constants.Timezones.REGIONS
import com.repleyva.tempus.domain.constants.Timezones.timezoneToCityMap
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

fun String?.cleanContent(): String {
    if (this == null) return ""
    val cleanXChars = replace(Regex("\\[\\+\\d+ chars]"), "")
    val cleanHTMLTags = cleanXChars.replace(Regex("<ul>|</ul>|<li>|</li>|<p>|</p>|<br>|<br/>"), "")
    val cleanedContent = cleanHTMLTags.replace(Regex("<[^>]*>"), "")
    return cleanedContent.trim()
}

fun String?.timezoneToCity(): String {
    val timezoneString = this ?: "GMT -05:00 Bogotá, CO (America)"
    val timezoneIndex = REGIONS.indexOfFirst { it.contains(timezoneString) }
    return timezoneToCityMap[timezoneIndex] ?: "Bogotá"
}