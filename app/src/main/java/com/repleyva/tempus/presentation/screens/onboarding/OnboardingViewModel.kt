package com.repleyva.tempus.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.use_cases.app_entry.AppEntryUseCases
import com.repleyva.tempus.presentation.screens.onboarding.OnboardingEvent.SaveAppEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
) : ViewModel() {

    fun eventHandler(event: OnboardingEvent) {
        when (event) {
            is SaveAppEntry -> saveAppEntry()
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }

}