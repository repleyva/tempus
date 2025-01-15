package com.repleyva.tempus.presentation.screens.settings

import com.repleyva.tempus.presentation.base.Event

sealed interface SettingsEvent : Event {

    data class UpdateDarkMode(val isDarkModeEnabled: Boolean) : SettingsEvent

    data class UpdateNickname(val newNickname: String) : SettingsEvent

    data class UpdateSelectedEmoji(val emoji: String) : SettingsEvent

    data class UpdateTimezone(val newTimezone: String) : SettingsEvent
}