package com.repleyva.tempus.domain.extensions

import com.repleyva.tempus.domain.error_mapper.ExceptionMapper
import com.repleyva.tempus.domain.errors.CommonError
import com.repleyva.tempus.domain.errors.ErrorDomain
import org.apache.http.conn.ConnectTimeoutException
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

fun Throwable.handleExceptionMapper(mapper: ExceptionMapper): ErrorDomain =
    this.toCommonError() ?: mapper.getGenericError(this)

fun Throwable.toCommonError(): ErrorDomain? = when (this) {
    is ConnectTimeoutException, is SocketTimeoutException, is IOException, is CancellationException -> CommonError.ConnectError
    else -> null
}