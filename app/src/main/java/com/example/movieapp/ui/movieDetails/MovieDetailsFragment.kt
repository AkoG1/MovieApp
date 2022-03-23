package com.example.movieapp.ui.movieDetails

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieDetailsFragmentBinding
import com.example.movieapp.extensions.setImage
import com.example.movieapp.model.MovieDetailsModel
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
        initListeners()
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(id = safeArgs.id)
    }

    private fun observe() {
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    successObserve(it.data)
                }
                is Resource.Error -> {
                    makeToastMessage(it.message!!)
                    binding.progressBar.isVisible = false
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                else -> {
                    makeToastMessage(getString(R.string.unknownError))
                }
            }
        }
    }

    private fun successObserve(it: MovieDetailsModel) {
        with(binding) {
            titleTV.text = it.title
            imdbRating.text = it.imdbRating
            lengthTextView.text = it.runtime
            languageTextView.text = it.language
            ratingTextView.text = it.rated
            description.text = it.plot
            cast.text = it.actors
            coverIV.setImage(it.poster)
            if (!it.genre.isNullOrEmpty()) {
                val genre = it.genre.split(",").map { it.trim() }
                when (genre.size) {
                    0 -> {}
                    1 -> genreTV.text = genre[0]
                    2 -> {
                        genreTV.text = genre[0]
                        genre2TV.isVisible = true
                        genre2TV.text = genre[1]
                    }
                    3 -> {
                        genreTV.text = genre[0]
                        genre2TV.isVisible = true
                        genre2TV.text = genre[1]
                        genre3TV.isVisible = true
                        genre3TV.text = genre[2]
                    }
                }
            }
            progressBar.isVisible = false
        }

    }
}