package com.repleyva.tempus.presentation.screens.settings

import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.repository.SettingsRepository
import com.repleyva.tempus.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateDarkMode
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateNickname
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateSelectedEmoji
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateTimezone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : SimpleMVIBaseViewModel<SettingsState, SettingsEvent>() {

    override fun initState(): SettingsState = SettingsState(
        theme = settingsRepository.getMode(),
        nickname = settingsRepository.getNickname(),
        selectedEmoji = settingsRepository.getSelectedEmoji(),
        selectedTimezone = settingsRepository.getTimezone()
    )

    override fun eventHandler(event: SettingsEvent) {
        when (event) {
            is UpdateDarkMode -> updateDarkMode(event.isDarkModeEnabled)
            is UpdateNickname -> updateNickname(event.newNickname)
            is UpdateSelectedEmoji -> updateSelectedEmoji(event.emoji)
            is UpdateTimezone -> updateTimezone(event.newTimezone)
        }
    }

    private fun updateDarkMode(isDarkModeEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDarkMode(isDarkModeEnabled)
        }
    }

    private fun updateNickname(newNickname: String) {
        viewModelScope.launch {
            settingsRepository.updateNickname(newNickname)
        }
    }

    private fun updateSelectedEmoji(emoji: String) {
        viewModelScope.launch {
            settingsRepository.updateSelectedEmoji(emoji)
        }
    }

    private fun updateTimezone(newTimezone: String) {
        viewModelScope.launch {
            settingsRepository.updateTimezone(newTimezone)
        }
    }

}
