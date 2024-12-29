package com.repleyva.tempus.presentation.screens.onboarding

sealed interface OnboardingEvent {

    data object SaveAppEntry: OnboardingEvent
}