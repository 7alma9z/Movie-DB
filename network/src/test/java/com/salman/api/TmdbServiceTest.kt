package com.salman.api

/**
@author Salman Aziz
created on 7/8/22
 **/


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.salman.TestMockResponseFileReader
import com.salman.remote.ApiResponse
import com.salman.remote.ApiResponseCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class TmdbServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: TmdbService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())

            .build()
            .create(TmdbService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

/*    @Test
    fun getMovies() {

        enqueueResponse(
            "success_movies.json"
        )
        runBlocking {
            val response = service.getMovies(Paths.POPULAR_MOVIES)
            assertThat( response is ApiResponse.ApiSuccessResponse).isTrue()

        }

    }*/


    @Test
    fun getMoviesDetails() {

        enqueueResponse(
            "success_movie_deatils.json"
        )
        runBlocking {
            val response = service.getMovieDetails(507086)
            assertThat( response is ApiResponse.ApiSuccessResponse).isTrue()

        }

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {

        val source = TestMockResponseFileReader(fileName)
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.content)
        )
    }
}