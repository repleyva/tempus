package com.repleyva.tempus.domain.common

import com.repleyva.tempus.domain.errors.ErrorDomain

sealed class DataState<T> {

    data class Error<T>(
        val error: ErrorDomain? = null,
    ) : DataState<T>()

    data class Data<T>(
        val data: T,
    ) : DataState<T>()

    data class Loading<T>(
        val isLoading: Boolean = false,
    ) : DataState<T>()
}