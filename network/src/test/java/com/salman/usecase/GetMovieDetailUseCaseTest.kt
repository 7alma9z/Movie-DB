package com.salman.usecase

import com.salman.api.Paths
import com.salman.data.repos.TmdbRepository
import com.salman.domain.response.TmdbListingResponse
import com.salman.remote.DataState
import com.salman.testUtil.mock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
 internal class GetMovieDetailUseCaseTest{

     @MockK
     lateinit var  tmdbRepository: TmdbRepository
     @Before
     fun setUp() {
         MockKAnnotations.init(this)
     }

     @org.junit.Test
     fun `test invoke() GetPostsUseCase gives MoviesList`() = runBlocking {
         // Given
         val getMoviesUseCase = GetMoviesUseCase(tmdbRepository)
         val mockResponse = mock<TmdbListingResponse>()

         // When
         coEvery { tmdbRepository.getMovies(Paths.POPULAR_MOVIES) }
             .returns(flowOf(DataState.Success(mockResponse)))

         // Invoke
         val moviesUseCase = getMoviesUseCase(Paths.POPULAR_MOVIES)



         val moviesDataState = moviesUseCase.first()
         MatcherAssert.assertThat(moviesDataState, CoreMatchers.notNullValue())
         MatcherAssert.assertThat(moviesDataState, CoreMatchers.instanceOf(DataState.Success::class.java))

         val moviesResponse = (moviesDataState as DataState.Success).data
         MatcherAssert.assertThat(moviesResponse, CoreMatchers.notNullValue())
         Assert.assertEquals(moviesResponse!!.results, mockResponse.results)

     }

     @org.junit.Test
     fun `test invoke() GetPostsUseCase gives Error`() = runBlocking {
         // Given
         val getMoviesUseCase = GetMoviesUseCase(tmdbRepository)

         val mockResponse = mock<DataState.CustomMessages.BadRequest>()
          // When
         coEvery { tmdbRepository.getMovies(Paths.POPULAR_MOVIES) }
             .returns(flowOf(DataState.Error(mockResponse)))

         // Invoke
         val moviesUseCase = getMoviesUseCase(Paths.POPULAR_MOVIES)



         val moviesDataState = moviesUseCase.first()
         MatcherAssert.assertThat(moviesDataState, CoreMatchers.notNullValue())
         MatcherAssert.assertThat(moviesDataState, CoreMatchers.instanceOf(DataState.Error::class.java))

         val moviesResponse = (moviesDataState as DataState.Error)
         MatcherAssert.assertThat(moviesResponse, CoreMatchers.notNullValue())
         Assert.assertEquals(moviesResponse!!.error, mockResponse)

     }


 }