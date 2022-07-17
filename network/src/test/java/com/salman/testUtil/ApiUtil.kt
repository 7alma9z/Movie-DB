package com.salman.testUtil

import com.salman.remote.ApiResponse
import retrofit2.Response
import java.lang.Exception

object ApiUtil {

    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    fun <T : Any> createCall(response: Response<T>): ApiResponse<T> =
        ApiResponse.create(200..229, response)

    fun <T : Any> failerCall(data: T) = createFailCall(Response.success(data))


    fun <T : Any> createFailCall(response: Response<T>): ApiResponse<T> =
        ApiResponse.exception(Exception())
}
