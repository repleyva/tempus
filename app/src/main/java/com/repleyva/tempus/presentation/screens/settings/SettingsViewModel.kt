package com.repleyva.tempus.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

/**
 * Todo: usar casos de uso para el repository
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val theme: StateFlow<Boolean> = settingsRepository.getMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun updateDarkMode(isDarkModeEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDarkMode(isDarkModeEnabled)
        }
    }

    val nickname: StateFlow<String> = settingsRepository.getNickname()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    fun updateNickname(newNickname: String) {
        viewModelScope.launch {
            settingsRepository.updateNickname(newNickname)
        }
    }

    val selectedEmoji: StateFlow<String> = settingsRepository.getSelectedEmoji()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "\uD83D\uDE36"
        )

    fun updateSelectedEmoji(emoji: String) {
        viewModelScope.launch {
            settingsRepository.updateSelectedEmoji(emoji)
        }
    }

    val selectedTimezone: StateFlow<String> = settingsRepository.getTimezone()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TimeZone.getDefault().id
        )

    fun updateTimezone(newTimezone: String) {
        viewModelScope.launch {
            settingsRepository.updateTimezone(newTimezone)
        }
    }
}
