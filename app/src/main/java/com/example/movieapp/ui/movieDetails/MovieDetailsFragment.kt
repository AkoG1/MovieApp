package com.example.movieapp.ui.movieDetails

import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieDetailsFragmentBinding
import com.example.movieapp.extensions.setImage
import com.example.movieapp.model.MovieDetailsModel
import com.example.movieapp.ui.base.BaseFragment
import com.example.movieapp.ui.movieDetails.adapter.ActorsAdapter
import com.example.movieapp.ui.movieDetails.vm.MovieDetailsViewModel
import com.example.movieapp.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment :
    BaseFragment<MovieDetailsFragmentBinding>(MovieDetailsFragmentBinding::inflate) {

    private val viewModel: MovieDetailsViewModel by viewModel()

    private val safeArgs: MovieDetailsFragmentArgs by navArgs()

    private lateinit var adapter: ActorsAdapter

    override fun init() {
        initListeners()
        initRecyclerView()
        getMovieDetails()
        observeWholeData()
        observeActorsDetails()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = ActorsAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(id = safeArgs.id)
    }

    private fun getActorsDetails(actors: String) {
        viewModel.getActorsDetails(actors = actors)
    }

    private fun observeWholeData() {
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    successObserve(it.data)
                }
                is Resource.Error -> {
                    it.message?.let { message -> makeToastMessage(message) }
                    binding.progressBar.isVisible = false
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Idle -> {}
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
            coverIV.setImage(it.poster)
            it.actors?.let { name -> getActorsDetails(name) }
            Log.d("12345", "successObserve: ${it.actors}")
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

    private fun observeActorsDetails() {
        viewModel.actorsDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.setData(it.data)
                    binding.actorsProgressBar.isVisible = false
                }
                is Resource.Error -> {
                    makeToastMessage(it.message!!)
                    binding.actorsProgressBar.isVisible = false
                }
                is Resource.Loading -> {
                    binding.actorsProgressBar.isVisible = true
                }
                is Resource.Idle -> {}
            }
        }
    }
}