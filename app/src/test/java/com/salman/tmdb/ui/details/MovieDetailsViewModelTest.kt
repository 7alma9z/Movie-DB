package com.salman.tmdb.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.salman.ApiHandler
import com.salman.domain.response.MovieDetailReponse
import com.salman.remote.DataState
import com.salman.testUtil.getOrAwaitValue
import com.salman.testUtil.mock
import com.salman.tmdb.MainCoroutineRule
import com.salman.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
internal class MovieDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private   var  apiHandlerMock = mock<ApiHandler>()

    private   lateinit var movieDetailsViewModelMock:MovieDetailsViewModel

    @Before
    fun setup() {
        movieDetailsViewModelMock= MovieDetailsViewModel(apiHandlerMock)
    }

    @Test
    fun `fetchData() should invoke movie live data successfully`() {
        val mockUseCase = mock<GetMovieDetailUseCase>()
        val resp = mock<MovieDetailReponse>()
        val mockResp = flow {
            emit(DataState.Success(resp))
        }

        Mockito.`when`(apiHandlerMock.getMovieDetail()).thenReturn(mockUseCase )
        runBlocking {
            Mockito.`when`(mockUseCase.invoke(1)).thenReturn(mockResp )
            movieDetailsViewModelMock.fetchData(1)
            Assert.assertTrue( movieDetailsViewModelMock.movies.getOrAwaitValue() == resp)
        }
    }
}