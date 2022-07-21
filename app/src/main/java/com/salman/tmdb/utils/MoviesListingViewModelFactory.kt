package com.salman.tmdb.utils

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.salman.ApiHandler
import com.salman.tmdb.ui.details.MovieDetailsBottomSheet
import com.salman.tmdb.ui.details.MovieDetailsViewModel
import com.salman.tmdb.ui.listingfragment.CategoriesFragment
import com.salman.tmdb.ui.listingfragment.MoviesListingViewModel


class MoviesListingViewModelFactory(
    private val owner: SavedStateRegistryOwner,
    private val apiHandler: ApiHandler,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        return when (owner) {
            is CategoriesFragment ->
                MoviesListingViewModel(apiHandler) as T
            is MovieDetailsBottomSheet ->
                MovieDetailsViewModel(apiHandler) as T
            else -> {
                MoviesListingViewModel(apiHandler) as T
            }
        }
    }
}