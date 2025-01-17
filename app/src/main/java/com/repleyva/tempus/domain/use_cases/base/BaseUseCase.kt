package com.repleyva.tempus.domain.use_cases.base

import com.repleyva.tempus.domain.common.CommonFlow
import com.repleyva.tempus.domain.common.DataState
import com.repleyva.tempus.domain.common.asCommonFlow
import com.repleyva.tempus.domain.error_mapper.ExceptionMapper
import com.repleyva.tempus.domain.error_mapper.GenericErrorMapper
import com.repleyva.tempus.domain.extensions.handleExceptionMapper
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.channelFlow

abstract class BaseUseCase() {

    protected fun <T> handlerErrorMapper(
        exceptionMapper: ExceptionMapper = GenericErrorMapper,
        action: suspend ProducerScope<DataState<T>>.() -> Unit,
    ): CommonFlow<DataState<T>> = channelFlow {
        executeAction(exceptionMapper, action)
    }.asCommonFlow()

    private suspend fun <T> ProducerScope<DataState<T>>.executeAction(
        exceptionMapper: ExceptionMapper,
        action: suspend ProducerScope<DataState<T>>.() -> Unit,
    ) = try {
        action()
    } catch (e: Exception) {
        e.printStackTrace()
        validateCustomError<T>(exceptionMapper, e)
    }

    private suspend fun <T> ProducerScope<DataState<T>>.validateCustomError(
        exceptionMapper: ExceptionMapper,
        exception: Exception,
    ) {
        val error = exception.handleExceptionMapper(exceptionMapper)
        send(DataState.Error(error))
    }
}