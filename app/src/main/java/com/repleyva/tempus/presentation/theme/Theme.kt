package com.repleyva.tempus.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = brand100Night,
    onPrimary = brand50Night,
    secondary = textSecondaryNight,
    tertiary = textPrimaryNight,
    background = backgroundPrimaryNight,
    error = systemErrorNight,
    surface = bottomNavigationBackgroundNight,
    onSurface = backgroundSecondaryNight,
)

private val LightColorScheme = lightColorScheme(
    primary = brand100Light,
    onPrimary = brand50Light,
    secondary = textSecondaryLight,
    tertiary = textPrimaryLight,
    background = backgroundPrimaryLight,
    error = systemErrorLight,
    surface = bottomNavigationBackgroundLight,
    onSurface = backgroundSecondaryLight,
)

@Composable
fun TempusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}