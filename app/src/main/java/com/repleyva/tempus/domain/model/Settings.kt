package com.repleyva.tempus.domain.model

import androidx.annotation.DrawableRes

data class DarkMode(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
    val isDarkModeEnabled: Boolean,
)

data class Timezone(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
)

data class AppVersion(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
)