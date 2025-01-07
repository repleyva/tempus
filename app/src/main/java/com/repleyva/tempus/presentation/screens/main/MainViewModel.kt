package com.repleyva.tempus.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.use_cases.app_entry.AppEntryUseCases
import com.repleyva.tempus.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.tempus.presentation.nav.NewsRouter
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
            is MainEvent.Init -> init()
        }
    }

    private fun init() {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->

            updateUi {
                copy(
                    startDestination = if (shouldStartFromHomeScreen) {
                        NewsRouter.NewsNavigation
                    } else {
                        NewsRouter.AppStartNavigation
                    }
                )
            }

            delay(300)

            updateUi { copy(splashCondition = false) }
        }.launchIn(viewModelScope)
    }
}