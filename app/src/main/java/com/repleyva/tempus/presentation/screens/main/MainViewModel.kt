package com.repleyva.tempus.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.use_cases.app_entry.AppEntryUseCases
import com.repleyva.tempus.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.tempus.presentation.nav.NewsRouter
import com.repleyva.tempus.presentation.screens.main.MainEvent.Init
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
) : SimpleMVIBaseViewModel<MainState, MainEvent>() {

    override fun initState(): MainState = MainState()

    override fun eventHandler(event: MainEvent) {
        when (event) {
            is Init -> init()
        }
    }

    private fun init() {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            updateDestination(shouldStartFromHomeScreen)
            delay(300)
            updateUi { copy(splashCondition = false) }
        }.launchIn(viewModelScope)
    }

    private fun updateDestination(shouldStartFromHomeScreen: Boolean) {
        updateUi {
            copy(
                startDestination = if (shouldStartFromHomeScreen) {
                    NewsRouter.NewsNavigation
                } else {
                    NewsRouter.AppStartNavigation
                }
            )
        }
    }
}