package com.repleyva.tempus.presentation.screens.main

import com.repleyva.tempus.presentation.base.State
import com.repleyva.tempus.presentation.nav.NewsRouter

data class MainState(
    val splashCondition: Boolean = true,
    val startDestination: NewsRouter = NewsRouter.AppStartNavigation,
) : State
