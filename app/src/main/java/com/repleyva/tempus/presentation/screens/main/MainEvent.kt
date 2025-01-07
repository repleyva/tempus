package com.repleyva.tempus.presentation.screens.main

import com.repleyva.tempus.presentation.base.Event

sealed interface MainEvent : Event {

    data object Init : MainEvent
}