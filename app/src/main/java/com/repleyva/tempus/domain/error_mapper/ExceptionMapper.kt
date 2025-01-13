package com.repleyva.tempus.domain.error_mapper

import com.repleyva.tempus.domain.constants.HTTP_BAD_REQUEST
import com.repleyva.tempus.domain.constants.HTTP_FAILED_DEPENDENCY
import com.repleyva.tempus.domain.errors.CommonError
import com.repleyva.tempus.domain.errors.ErrorDomain
import com.repleyva.tempus.domain.model.ErrorEntity

abstract class ExceptionMapper {

    open fun getCustomError(errorEntity: ErrorEntity): ErrorDomain =
        when (errorEntity.error.statusCode) {
            in HTTP_BAD_REQUEST..HTTP_FAILED_DEPENDENCY -> CommonError.GenericServiceError(
                errorEntity.error.description,
                errorEntity.error.title,
                errorEntity.error.message
            )
            else -> CommonError.GenericServiceError()
        }

    open fun getGenericError(exception: Throwable): ErrorDomain = CommonError.GenericServiceError()
}