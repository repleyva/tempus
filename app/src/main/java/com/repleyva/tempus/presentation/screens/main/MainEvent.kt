package com.repleyva.tempus.presentation.screens.main

import com.repleyva.tempus.presentation.base.Event

interface MainEvent : Event {

    data object Init : MainEvent
}