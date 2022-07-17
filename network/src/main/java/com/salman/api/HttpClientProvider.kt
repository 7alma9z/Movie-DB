package com.salman.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
@author Salman Aziz
created on 7/2/22
 **/

internal class HttpClientProvider(val interceptor: AuthInterceptor) {

    val okHttpClient: OkHttpClient by lazy { createOkHttpClient() }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_VALUE_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_VALUE_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_VALUE_SECONDS, TimeUnit.SECONDS)
            .callTimeout(TIMEOUT_VALUE_SECONDS, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        builder.addInterceptor(interceptor)

        return builder.build()

    }

    companion object {
        private const val TIMEOUT_VALUE_SECONDS = 10L
    }
}