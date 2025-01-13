package com.repleyva.tempus.domain.errors

import kotlinx.serialization.Serializable

@Serializable
sealed class CommonError : ErrorDomain() {

    @Serializable
    data object ConnectError : CommonError()

    @Serializable
    data class GenericServiceError(
        val description: String? = null,
        val title: String? = null,
        val message: String? = null,
    ) : CommonError()

}

fun ErrorDomain?.orGenericError() = this ?: CommonError.GenericServiceError()