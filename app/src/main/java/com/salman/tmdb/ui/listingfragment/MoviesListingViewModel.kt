package com.salman.tmdb.ui.listingfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.salman.ApiHandler
import com.salman.api.Paths
import com.salman.domain.response.TmdbListingResponse
import com.salman.remote.DataState
import com.salman.tmdb.ui.BaseMoviesListingViewModel
import com.salman.tmdb.utils.FragmentType
import kotlinx.coroutines.*

/**
@author Salman Aziz
created on 7/13/22
 **/

class MoviesListingViewModel(private val apiHandler: ApiHandler) : BaseMoviesListingViewModel() {

    private val _headerImage = MutableLiveData<String>()
    val headerImage: LiveData<String> = _headerImage
    private lateinit var job: Job

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() {
        _mainLoader.postValue(false)
        if (::job.isInitialized)
            job.cancel()

    }

    fun checkForPooling(canPoolData: Boolean, isChecked: Boolean, fragmentType: FragmentType) {
        if (canPoolData && !isChecked) {
            cancelJob()
        } else if (canPoolData && isChecked) {
            fetchMovies(fragmentType, canPoolData)
        } else if (movies.value == null) {
            fetchMovies(fragmentType, canPoolData)
        }
    }


    override fun fetchMovies(fragmentType: FragmentType, canPool: Boolean) {
        job = viewModelScope.launch(Dispatchers.IO) {

            if (canPool) {
                while (isActive) {
                    fetchData(fragmentType)
                    delay(30000)
                }
            } else {
                fetchData(fragmentType)
            }
        }


    }


    private suspend fun fetchData(fragmentType: FragmentType) {
        when (fragmentType) {
            FragmentType.latest -> {
                requestNetworkForMovies(Paths.LATEST_MOVIES, true)
            }
            FragmentType.upcoming -> {
                requestNetworkForMovies(Paths.UPCOMING_MOVIES)
            }
            FragmentType.topRated -> {
                requestNetworkForMovies(Paths.TOP_RATED_MOVIES)
            }
            FragmentType.popular -> {
                requestNetworkForMovies(Paths.POPULAR_MOVIES)
            }
        }
    }

    private suspend fun requestNetworkForMovies(path: String, setHeader: Boolean = false) {

        apiHandler.getMoviesUseCase().invoke(path).collect { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    _mainLoader.postValue(true)
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _movies.postValue(it)
                        if (setHeader)
                            setHeader(it)
                    }
                    _mainLoader.postValue(false)

                }
                is DataState.Error -> {
                    _errorMessage.postValue(dataState.error)
                    _mainLoader.postValue(false)

                }
            }
        }


    }


    private fun setHeader(tmdbListingResponse: TmdbListingResponse) {
        tmdbListingResponse.results.first().backdropPath?.let {
            _headerImage.postValue(it)
        }

    }

}