package com.salman

import com.salman.api.AuthInterceptor
import com.salman.api.HttpClientProvider
import com.salman.api.NetworkTransport
import com.salman.api.TmdbService
import com.salman.data.repoimpl.TmdbRepositoryImpl
import com.salman.data.repos.TmdbRepository
import com.salman.usecase.*

/**
@author Salman Aziz
created on 7/2/22
 **/

class ApiHandler private constructor(val apiKey: String) {

    init {
        instance = this
    }

    companion object {
        private var instance: ApiHandler? = null
        fun getInstance(apiKey: String): ApiHandler {
            synchronized(CorApplication::class.java) {
                if (instance == null)
                    instance =
                        ApiHandler(apiKey)

            }
            return instance!!
        }
    }

    private fun provideNetworkClient() = HttpClientProvider(getAuthInterceptor()).okHttpClient
    private fun getNetworkTransport() = NetworkTransport(provideNetworkClient())
    private fun getPostsApi() = getNetworkTransport().provideApi<TmdbService>()
    private fun getPostRepository() = TmdbRepositoryImpl(getPostsApi())
    private fun getAuthInterceptor() = AuthInterceptor(apiKey)
    private val tmdbRepository: TmdbRepository by lazy {
        getPostRepository()
    }

    fun getMoviesUseCase() =
        GetMoviesUseCase(tmdbRepository)

    fun getMovieDetail() =
        GetMovieDetailUseCase(tmdbRepository)


}