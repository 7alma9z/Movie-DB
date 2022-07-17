package com.salman.data.repoimpl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.salman.api.Paths
import com.salman.api.TmdbService
import com.salman.domain.response.MovieDetailReponse
import com.salman.domain.response.TmdbListingResponse
import com.salman.remote.DataState
import com.salman.testUtil.ApiUtil.failerCall
import com.salman.testUtil.ApiUtil.successCall
import com.salman.testUtil.mock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class TmdbRepositoryImplTest {
    // Subject under test
    private lateinit var repository: TmdbRepositoryImpl

    @MockK
    private lateinit var tmdbService: TmdbService


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = TmdbRepositoryImpl(tmdbService)

    }

    @org.junit.Test
    fun `test getMovies() gives Error`() = runBlocking {


        val mockProducts = mock<TmdbListingResponse>()
        val apiCall = failerCall(mockProducts)

        coEvery { tmdbService.getMovies(Paths.POPULAR_MOVIES) }
            .returns(apiCall)

        val actual = mutableListOf<DataState<TmdbListingResponse>>()

        repository.getMovies(Paths.POPULAR_MOVIES).take(2).collect {
            actual.add(it)
        }

        MatcherAssert.assertThat(
            actual[0],
            CoreMatchers.instanceOf(DataState.Loading::class.java)
        )

        val moviesDataState = actual[1]
        MatcherAssert.assertThat(moviesDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            moviesDataState,
            CoreMatchers.instanceOf(DataState.Error::class.java)
        )

        val moviesResponseObject = (moviesDataState as DataState.Error).data
        MatcherAssert.assertThat(moviesResponseObject, CoreMatchers.nullValue())
        MatcherAssert.assertThat(
            moviesResponseObject,
            CoreMatchers.nullValue()
        )

        coVerify(exactly = 1) { tmdbService.getMovies(Paths.POPULAR_MOVIES) }
        confirmVerified(tmdbService)
    }

    @org.junit.Test
    fun `test getMovies() gives Object of TmdbListingResponse`() = runBlocking {


        val mockProducts = mock<TmdbListingResponse>()
        val apiCall = successCall(mockProducts)

        coEvery { tmdbService.getMovies(Paths.POPULAR_MOVIES) }
            .returns(apiCall)

        val actual = mutableListOf<DataState<TmdbListingResponse>>()

        repository.getMovies(Paths.POPULAR_MOVIES).take(2).collect {
            actual.add(it)
        }

        MatcherAssert.assertThat(
            actual[0],
            CoreMatchers.instanceOf(DataState.Loading::class.java)
        )

        val moviesDataState = actual[1]
        MatcherAssert.assertThat(moviesDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            moviesDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val moviesResponseObject = (moviesDataState as DataState.Success).data
        MatcherAssert.assertThat(moviesResponseObject, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            moviesResponseObject!!.results,
            CoreMatchers.`is`(mockProducts.results)
        )

        coVerify(exactly = 1) { tmdbService.getMovies(Paths.POPULAR_MOVIES) }
        confirmVerified(tmdbService)
    }

    @org.junit.Test
    fun `test getMovieDetails() gives Detailed Movie Object`() =


        runBlocking {
            val mockResp = mock<MovieDetailReponse>()
            val apiCall = successCall(mockResp)

            // When
            coEvery { tmdbService.getMovieDetails(1) }
                .returns(apiCall)
            // Invoke
            val apiResponseFlow = repository.getMovieDetails(1)
            // Then
            MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

            val actual = mutableListOf<DataState<MovieDetailReponse>>()

            apiResponseFlow.take(2).collect {
                actual.add(it)
            }


            val loadingDataState = actual[0]
            MatcherAssert.assertThat(loadingDataState, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(
                loadingDataState,
                CoreMatchers.instanceOf(DataState.Loading::class.java)
            )

            val postsListDataState = actual[1]
            MatcherAssert.assertThat(postsListDataState, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(
                postsListDataState,
                CoreMatchers.instanceOf(DataState.Success::class.java)
            )

            val productsList = (postsListDataState as DataState.Success).data
            MatcherAssert.assertThat(productsList, CoreMatchers.notNullValue())



            coVerify(exactly = 1) { tmdbService.getMovieDetails(1) }
            confirmVerified(tmdbService)
        }


}