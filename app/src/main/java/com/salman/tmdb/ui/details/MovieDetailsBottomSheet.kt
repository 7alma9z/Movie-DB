package com.salman.tmdb.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.salman.ApiHandler
import com.salman.domain.response.Genre
import com.salman.domain.response.MovieDetailReponse
import com.salman.tmdb.BuildConfig
import com.salman.tmdb.R
import com.salman.tmdb.databinding.BottomSheetMovieDetailBinding
import com.salman.tmdb.extensions.setImageUrl
import com.salman.tmdb.extensions.showSnackBar
import com.salman.tmdb.extensions.visible
import com.salman.tmdb.ui.DataCallBack
import com.salman.tmdb.utils.MoviesListingViewModelFactory
import com.salman.tmdb.utils.Utils
import com.salman.tmdb.utils.Utils.W500

/**
@author Salman Aziz
created on 7/17/22
 **/

class MovieDetailsBottomSheet : BottomSheetDialogFragment() {
    lateinit var dataCallBack: DataCallBack

    var movieId: Int = -1
    lateinit var binding: BottomSheetMovieDetailBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        MoviesListingViewModelFactory(this, apiHandler = ApiHandler.getInstance(BuildConfig.API_KEY))
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            MovieDetailsBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }

        val TAG = MovieDetailsBottomSheet::class.java.simpleName
        val ID = "movieId"

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataCallBack) {
            dataCallBack = context
        } else {
            throw RuntimeException("${context.javaClass.simpleName} should implement DataCallBack")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ID, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return BottomSheetMovieDetailBinding.inflate(inflater).apply {
            binding = this
            movieDetailsViewModel.fetchData(movieId)
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObserving()
    }

    private fun startObserving() {
        movieDetailsViewModel.movies.observe(viewLifecycleOwner) {
            it?.let {
                binding.contentGroup.visible()
                updateUI(it)
            }
        }
        movieDetailsViewModel.mainLoader.observe(viewLifecycleOwner) {
            showLoader(it)
        }
        movieDetailsViewModel.errorMessage.observe(viewLifecycleOwner) {
            dismiss()
            dataCallBack.onError(it)
        }

    }

    private fun updateUI(movie: MovieDetailReponse) {

        binding.ivPoster.setImageUrl(
            Utils.getImagePath(W500,movie.backdropPath), placeholder = AppCompatResources
                .getDrawable(requireContext(), R.drawable.image_place_holder)
        )
        binding.title.text = movie.title
        binding.description.text = movie.overview
        binding.icPlay.apply {
            visible().takeIf { movie.video }
            setOnClickListener {
                binding.coordinator.showSnackBar(movie.title)
            }
        }
        addGeneres(movie.genres)
    }

    private fun addGeneres(genres: List<Genre>) {

        genres.forEach {
            Chip(requireContext()).apply {
                this.text = it.name
                binding.genres.addView(this)
            }
        }

    }

    private fun showLoader(show: Boolean) {
        binding.progress.visibility = View.VISIBLE.takeIf { show } ?: View.GONE
    }
}