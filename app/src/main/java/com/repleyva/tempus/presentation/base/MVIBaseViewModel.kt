package com.repleyva.tempus.presentation.base

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.repleyva.tempus.domain.extensions.SingleMutableSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class MVIBaseViewModel<S : State, E : Event, A : Action> : ViewModel() {

    private val _uiState: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }
    val uiState get() = _uiState.asStateFlow()

    private val _uiAction: MutableSharedFlow<A> by lazy { SingleMutableSharedFlow() }
    val uiAction get() = _uiAction.asSharedFlow()

    abstract fun initState(): S

    abstract fun eventHandler(event: E)

    protected fun updateUi(
        handler: S.() -> S,
    ) {
        _uiState.update(handler)
    }

    protected fun updateAction(
        newAction: A,
    ) {
        _uiAction.tryEmit(newAction)
    }

}

interface Event

interface Action

@Stable
interface State