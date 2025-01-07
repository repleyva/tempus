package com.repleyva.tempus.presentation.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.AppVersion
import com.repleyva.tempus.domain.model.DarkMode
import com.repleyva.tempus.domain.model.Timezone
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

fun Context.openUrl(url: String) {
    Intent(Intent.ACTION_VIEW).also {
        it.data = Uri.parse(url)
        if (it.resolveActivity(packageManager) != null) {
            startActivity(it)
        }
    }
}

fun Context.shareUrl(url: String) {
    Intent(Intent.ACTION_SEND).also {
        it.putExtra(Intent.EXTRA_TEXT, url)
        it.type = "text/plain"
        if (it.resolveActivity(packageManager) != null) {
            startActivity(it)
        }
    }
}

fun Context.darkMode(
    isDarkModeEnabled: Boolean,
) = DarkMode(
    title = getString(R.string.dark_mode),
    description =
    if (isDarkModeEnabled) getString(R.string.dark_mode_on)
    else getString(R.string.dark_mode_off),
    isDarkModeEnabled = isDarkModeEnabled,
    icon =
    if (isDarkModeEnabled) R.drawable.ic_mode_dark
    else R.drawable.ic_mode_light
)

fun Context.timeZone(
    selectedTimezone: String,
) = Timezone(
    title = getString(R.string.timezone),
    description = selectedTimezone,
    icon = R.drawable.ic_timezone
)

fun Context.appVersion(
    appVersion: String,
) = AppVersion(
    title = getString(R.string.app_version),
    description = appVersion,
    icon = R.drawable.ic_about,
)

fun Context.getAppVersionName(): String = runCatching {
    packageManager.getPackageInfo(packageName, 0).versionName
}.getOrElse { "" }