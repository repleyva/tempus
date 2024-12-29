package com.repleyva.tempus.presentation.screens.main

import com.repleyva.tempus.presentation.base.Action

interface MainAction: Action {

    data object Idle: MainAction
}