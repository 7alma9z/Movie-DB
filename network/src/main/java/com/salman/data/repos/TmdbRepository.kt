package com.salman.data.repos

import com.salman.domain.response.MovieDetailReponse
import com.salman.domain.response.TmdbListingResponse
import com.salman.remote.DataState
import kotlinx.coroutines.flow.Flow
import java.nio.file.Path

interface TmdbRepository {
    suspend fun getMovies(path: String): Flow<DataState<TmdbListingResponse>>
    suspend fun getMovieDetails(movieId: Int):
            Flow<DataState<MovieDetailReponse>>

}