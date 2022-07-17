package com.salman.tmdb.ui.listingfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.salman.ApiHandler
import com.salman.tmdb.BuildConfig
import com.salman.tmdb.databinding.FragmentCategoriesBinding
import com.salman.tmdb.ui.DataCallBack
import com.salman.tmdb.utils.FragmentType
import com.salman.tmdb.utils.MoviesListingViewModelFactory
import com.salman.tmdb.utils.ToggleImageButton


const val ARG_CAN_POOL_DATA = "canPoolData"
const val ARG_EXPANDED = "expanded"
const val ARG_SCREEN_TITLE = "title"
const val ARG_FRAGMENT_TYPE = "type"


class CategoriesFragment : Fragment() {
    private val moviesListingListingViewModel: MoviesListingViewModel by viewModels {
        MoviesListingViewModelFactory(this, apiHandler = ApiHandler.getInstance(BuildConfig.API_KEY))
    }

    private var canPoolData: Boolean = false
    private var shouldExpandByDefault: Boolean = false
    private var title: String? = null
    private var type: FragmentType = FragmentType.latest

    private lateinit var binding: FragmentCategoriesBinding
    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter().apply {
            this.movieClickListener = {
                dataCallBack.onMovieSelection(it.id)
            }
        }
    }
    lateinit var dataCallBack: DataCallBack

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
            canPoolData = it.getBoolean(ARG_CAN_POOL_DATA, false)
            shouldExpandByDefault = it.getBoolean(ARG_EXPANDED, false)
            title = it.getString(ARG_SCREEN_TITLE)
            type = FragmentType.valueOf(it.getString(ARG_FRAGMENT_TYPE, FragmentType.latest.name))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentCategoriesBinding.inflate(LayoutInflater.from(requireContext()), container, false).apply {
            binding = this
            title.text = this@CategoriesFragment.title
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies.adapter = adapter
        binding.btnEnabler.onCheckedChangeListener = onCheckedChangeListener

        if (shouldExpandByDefault)
            fetchMovies(type, canPoolData)



        startObserving()

    }

    private fun fetchMovies(type: FragmentType, canPoolData: Boolean) {
        moviesListingListingViewModel.fetchMovies(type, canPoolData)

    }

    private val onCheckedChangeListener = object : ToggleImageButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: ToggleImageButton?, isChecked: Boolean) {
            moviesListingListingViewModel.checkForPooling(canPoolData, isChecked, type)
            binding.rvMovies.visibility = View.VISIBLE.takeIf { isChecked } ?: View.GONE
        }
    }

    private fun startObserving() {
        moviesListingListingViewModel.movies.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it.results)
                handleToggleState()

            }
        }
        moviesListingListingViewModel.mainLoader.observe(viewLifecycleOwner) {
            showLoader(it)
        }
        moviesListingListingViewModel.errorMessage.observe(viewLifecycleOwner) {
            dataCallBack.onError(it)
        }
        moviesListingListingViewModel.headerImage.observe(viewLifecycleOwner) {
            dataCallBack.setHeader(it)
        }

    }

    private fun showLoader(show: Boolean) {
        binding.progress.visibility = View.VISIBLE.takeIf { show } ?: View.GONE
    }

    private fun handleToggleState() {
        if (shouldExpandByDefault && !binding.btnEnabler.isChecked) {
            binding.btnEnabler.onCheckedChangeListener = null
            binding.btnEnabler.isChecked = true
            binding.btnEnabler.onCheckedChangeListener = onCheckedChangeListener
            binding.rvMovies.visibility = View.VISIBLE

        }
    }
}

