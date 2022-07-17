package com.salman.api

import com.salman.domain.response.MovieDetailReponse
import com.salman.domain.response.TmdbListingResponse
import com.salman.remote.ApiResponse
import retrofit2.http.*

internal interface TmdbService {



    @Headers("Accept: application/json")
    @GET("movie/{path}")
    suspend fun getMovies(@Path("path") path: String): ApiResponse<TmdbListingResponse>

    @Headers("Accept: application/json")
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int,):
            ApiResponse<MovieDetailReponse>

}