package com.salman.tmdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salman.domain.response.TmdbListingResponse
import com.salman.remote.DataState
import com.salman.tmdb.utils.FragmentType

/**
@author Salman Aziz
created on 7/13/22
 **/

abstract class BaseMoviesListingViewModel: ViewModel() {

    protected val _movies = MutableLiveData<TmdbListingResponse>()
    val movies: LiveData<TmdbListingResponse> = _movies
    protected val _mainLoader = MutableLiveData<Boolean>()
    val mainLoader: LiveData<Boolean> = _mainLoader
    protected val _errorMessage = MutableLiveData<DataState.CustomMessages>()
    val errorMessage: LiveData<DataState.CustomMessages> = _errorMessage


    abstract fun fetchMovies(fragmentType: FragmentType,canPool:Boolean=false)

}