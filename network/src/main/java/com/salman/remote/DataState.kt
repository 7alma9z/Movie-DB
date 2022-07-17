package com.salman.remote


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

sealed class DataState<T>(
    var data: T? = null,
    val error: CustomMessages = CustomMessages.SomethingWentWrong("Something Went Wrong")
) {
    class Success<T>(data: T?) : DataState<T>(data)
    class Loading<T> : DataState<T>()
    class Error<T>(customMessages: CustomMessages) : DataState<T>(error = customMessages)
    sealed class CustomMessages(val message: String = "") {

        data class Timeout(val error: String) : CustomMessages(message = error)
        data class EmptyData (val error: String) : CustomMessages(message = error)
        data class ServerBusy (val error: String) : CustomMessages(message = error)
        data class HttpException(val error: String) : CustomMessages(message = error)
        data class SocketTimeOutException(val error: String) : CustomMessages(message = error)
        data class NoInternet(val error: String) : CustomMessages(message = error)
        data class Unauthorized(val error: String) : CustomMessages(message = error)
        data class  InternalServerError(val error: String) : CustomMessages(message = error)
        data class  BadRequest(val error: String) : CustomMessages(message = error)
        data class  Conflict(val error: String) : CustomMessages(message = error)
        data class  NotFound(val error: String) : CustomMessages(message = error)
        data class  NotAcceptable(val error: String) : CustomMessages(message = error)
        data class ServiceUnavailable(val error: String) : CustomMessages(message = error)
        data class Forbidden(val error: String) : CustomMessages(message = error)
        data class SomethingWentWrong(val error: String) : CustomMessages(message = error)
    }
}
