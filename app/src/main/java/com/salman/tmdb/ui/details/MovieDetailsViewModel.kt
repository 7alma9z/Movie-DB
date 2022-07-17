package com.salman.tmdb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.ApiHandler
import com.salman.domain.response.MovieDetailReponse
import com.salman.remote.DataState
import kotlinx.coroutines.*

/**
@author Salman Aziz
created on 7/13/22
 **/

class MovieDetailsViewModel(private val apiHandler: ApiHandler) : ViewModel() {

    private val _movie = MutableLiveData<MovieDetailReponse>()
    val movies: LiveData<MovieDetailReponse> = _movie
    private val _mainLoader = MutableLiveData<Boolean>()
    val mainLoader: LiveData<Boolean> = _mainLoader
    private val _errorMessage = MutableLiveData<DataState.CustomMessages>()
    val errorMessage: LiveData<DataState.CustomMessages> = _errorMessage



    fun fetchData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            requestNetworkForMoviesDetail(id)
        }
    }

    private suspend fun requestNetworkForMoviesDetail(id: Int) {

        apiHandler.getMovieDetail().invoke(id).collect { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    _mainLoader.postValue(true)
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _movie.postValue(it)

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


}