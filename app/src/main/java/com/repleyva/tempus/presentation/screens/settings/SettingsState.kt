package com.repleyva.tempus.presentation.screens.settings

import com.repleyva.tempus.presentation.base.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class SettingsState(
    val theme: Flow<Boolean> = emptyFlow(),
    val nickname: Flow<String> = emptyFlow(),
    val selectedEmoji: Flow<String> = emptyFlow(),
    val selectedTimezone: Flow<String> = flowOf("GMT -05:00 Bogotá, CO (America)"),
) : State