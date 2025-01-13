package com.repleyva.tempus.presentation.screens.home

import com.repleyva.tempus.presentation.base.Event

sealed interface HomeEvent : Event {

    data object UpdateCity : HomeEvent

    data object FetchWeatherData : HomeEvent
}