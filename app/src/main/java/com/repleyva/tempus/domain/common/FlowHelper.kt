package com.repleyva.tempus.domain.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {

    fun collectCommon(
        coroutineScope: CoroutineScope?,
        callback: suspend (T) -> Unit,
    ) = onEach(callback::invoke)
        .launchIn(coroutineScope ?: CoroutineScope(Main))
}