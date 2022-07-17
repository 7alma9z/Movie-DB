package com.salman.usecase

import com.salman.data.repos.TmdbRepository

/**
@author Salman Aziz
created on 7/2/22
 **/

class GetMovieDetailUseCase(private val tmdbRepository: TmdbRepository) {
    suspend operator fun invoke( movieId:Int) = tmdbRepository.getMovieDetails( movieId)
}