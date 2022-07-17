package com.salman.api

import com.salman.remote.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
@author Salman Aziz
created on 7/2/22
 **/

internal class NetworkTransport(private val okHttpClient: OkHttpClient) {

    val retrofit: Retrofit by lazy { createBaseRestAdapter() }

    private fun createBaseRestAdapter(): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())

            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

     inline fun <reified T> provideApi(): T {
        return retrofit.create(T::class.java)
    }

}
