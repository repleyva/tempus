package com.repleyva.tempus.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorEntity(
    @SerialName("error") val error: ErrorDataEntity = ErrorDataEntity(),
)

@Serializable
data class ErrorDataEntity(
    @SerialName("message") val message: String? = "",
    @SerialName("title") val title: String? = "",
    @SerialName("description") val description: String? = null,
    @SerialName("name") val name: String? = "",
    @SerialName("statusCode") val statusCode: Int = 0,
)