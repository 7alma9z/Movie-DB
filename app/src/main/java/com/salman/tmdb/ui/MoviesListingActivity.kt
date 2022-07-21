package com.salman.tmdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
 import androidx.fragment.app.*
import com.salman.remote.DataState
import com.salman.tmdb.R
import com.salman.tmdb.databinding.ActivityMainBinding
import com.salman.tmdb.extensions.setImageUrl
import com.salman.tmdb.extensions.showSnackBar
import com.salman.tmdb.ui.details.MovieDetailsBottomSheet
import com.salman.tmdb.ui.listingfragment.*
import com.salman.tmdb.utils.FragmentType
import com.salman.tmdb.utils.Utils
import com.salman.tmdb.utils.Utils.W500

class MoviesListingActivity : AppCompatActivity(), DataCallBack {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                addFragment<CategoriesFragment>(
                    this, R.id.frag_latest,
                    getString(R.string.str_latest_movies),
                    true, true, FragmentType.latest
                )
                addFragment<CategoriesFragment>(
                    this,
                    R.id.frag_popular,
                    getString(R.string.str_popular_movies),
                    false, true,
                    FragmentType.popular
                )
                addFragment<CategoriesFragment>(
                    this,
                    R.id.frag_top_rated,
                    getString(R.string.str_top_rated_movies),
                    false, false,
                    FragmentType.topRated
                )
                addFragment<CategoriesFragment>(
                    this,
                    R.id.frag_upcoming,
                    getString(R.string.str_upcoming_movies),
                    false, false,
                    FragmentType.upcoming
                )
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }


    private inline fun <reified T : Fragment> addFragment(
        fragmentTransaction: FragmentTransaction,
        id: Int,
        title: String,
        canPool: Boolean,
        expanded: Boolean,
        type: FragmentType
    ) {
        Bundle().apply {
            putBoolean(ARG_CAN_POOL_DATA, canPool)
            putBoolean(ARG_EXPANDED, expanded)
            putString(ARG_SCREEN_TITLE, title)
            putString(ARG_FRAGMENT_TYPE, type.name)
            fragmentTransaction.add<T>(id, args = this)
        }
    }

    override fun setHeader(headerPath: String) {
        binding.ivPoster.setImageUrl(
            Utils.getImagePath(W500,headerPath), placeholder = AppCompatResources
                .getDrawable(this, R.drawable.image_place_holder)
        )
    }

    override fun onError(messages: DataState.CustomMessages) {
        binding.coordinator.showSnackBar(messages.message)
    }

    override fun onMovieSelection(id: Int) {
        showMovieDetail(id)
    }

    private fun showMovieDetail(id: Int) {
        MovieDetailsBottomSheet.newInstance(id).apply {
            show(supportFragmentManager, MovieDetailsBottomSheet.TAG)
        }
    }

}