package com.salman.remote

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    BadRequest(400),
    NotFound(404),
    Conflict(409),
    InternalServerError(500),
    Forbidden(403),
    NotAcceptable(406),
    ServiceUnavailable(503),
    UnAuthorized(401),
}

fun <T : Any> handleException(exception: Exception): DataState.CustomMessages {

    return when (exception) {
        is HttpException -> DataState.CustomMessages.HttpException(exception.message?:"Something went wrong")
        is TimeoutException -> DataState.CustomMessages.Timeout(exception.message?:"Something went wrong")
         is UnknownHostException -> DataState.CustomMessages.ServerBusy(exception.message?:"Something went wrong")
        is ConnectException -> DataState.CustomMessages.NoInternet(exception.message?:"Something went wrong")
        is SocketTimeoutException -> DataState.CustomMessages.SocketTimeOutException(exception.message?:"Something went wrong")
        else -> DataState.CustomMessages.NoInternet(exception.message?:"Something went wrong")
    }
}


fun  handleException(statusCode: Int, message: String): DataState.CustomMessages {
    return getErrorType(statusCode, message)
}

private fun getErrorType(code: Int, message: String): DataState.CustomMessages {
    return when (code) {
        ErrorCodes.SocketTimeOut.code -> DataState.CustomMessages.Timeout(message)
        ErrorCodes.UnAuthorized.code -> DataState.CustomMessages.Unauthorized(message)
        ErrorCodes.InternalServerError.code -> DataState.CustomMessages.InternalServerError(message)

        ErrorCodes.BadRequest.code -> DataState.CustomMessages.BadRequest(message)
        ErrorCodes.Conflict.code -> DataState.CustomMessages.Conflict(message)
        ErrorCodes.InternalServerError.code -> DataState.CustomMessages.InternalServerError(message)

        ErrorCodes.NotFound.code -> DataState.CustomMessages.NotFound(message)
        ErrorCodes.NotAcceptable.code -> DataState.CustomMessages.NotAcceptable(message)
        ErrorCodes.ServiceUnavailable.code -> DataState.CustomMessages.ServiceUnavailable(message)
        ErrorCodes.Forbidden.code -> DataState.CustomMessages.Forbidden(message)
        else -> DataState.CustomMessages.SomethingWentWrong(message)
    }
}
