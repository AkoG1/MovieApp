package com.example.movieapp.ui.movieDetails

import androidx.navigation.fragment.navArgs
import com.example.movieapp.databinding.MovieDetailsFragmentBinding
import com.example.movieapp.extensions.setImage
import com.example.movieapp.ui.base.BaseFragment
import com.example.movieapp.ui.movieDetails.vm.MovieDetailsViewModel
import com.example.movieapp.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment :
    BaseFragment<MovieDetailsFragmentBinding>(MovieDetailsFragmentBinding::inflate) {

    private val viewModel: MovieDetailsViewModel by viewModel()

    private val safeArgs: MovieDetailsFragmentArgs by navArgs()

    override fun init() {
        getMovieDetails()
        observe()
        swipeRefreshListener()
    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(id = safeArgs.id)
    }

    private fun observe() {
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    with(binding) {
                        titleTV.text = it.data.title
                        imdbRating.text = it.data.imdbRating
                        genreTV.text = it.data.genre
                        lengthTextView.text = it.data.runtime
                        languageTextView.text = it.data.language
                        ratingTextView.text = it.data.rated
                        description.text = it.data.plot
                        cast.text = it.data.actors
                        coverIV.setImage(it.data.poster)
                        swipeRefresh.isRefreshing = false
                    }
                }
                is Resource.Error -> {
                    makeToastMessage(it.message!!)
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Idle -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        }
    }

    private fun swipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.swipeRefreshListener(safeArgs.id)
        }
    }
}