package com.repleyva.tempus.presentation.screens.home

import com.repleyva.tempus.presentation.base.Event

sealed interface HomeEvent : Event {

    data class FetchWeatherData(val city: String? = null) : HomeEvent
}