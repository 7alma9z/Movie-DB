package com.salman.data.repoimpl

import com.salman.api.TmdbService
import com.salman.data.repos.TmdbRepository
import com.salman.domain.response.MovieDetailReponse
import com.salman.domain.response.TmdbListingResponse
import com.salman.remote.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
@author Salman Aziz
created on 7/2/22
 **/

internal class TmdbRepositoryImpl(private val tmdbService: TmdbService) : TmdbRepository {

    override suspend fun getMovies(path: String): Flow<DataState<TmdbListingResponse>> =flow{
        emit(DataState.Loading())
        tmdbService.getMovies(path).onSuccessSuspend({
            emit(this)
        }, {
            emit(this)
        }).onErrorSuspend {
            emit(error())
        }.onExceptionSuspend {
            emit(error())
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<DataState<MovieDetailReponse>> =flow{
        emit(DataState.Loading())

        tmdbService.getMovieDetails(movieId).onSuccessSuspend({
            emit(this)
        }, {
            emit(this)
        }).onErrorSuspend {
            emit(error())
        }.onExceptionSuspend {
            emit(error())
        }
    }
}