package com.example.movieapp.presentation.ui.movieDetails

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieDetailsFragmentBinding
import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.utils.isOnline
import com.example.movieapp.presentation.base.BaseFragment
import com.example.movieapp.presentation.ui.movieDetails.adapter.ActorsAdapter
import com.example.movieapp.presentation.ui.movieDetails.vm.MovieDetailsViewModel
import com.example.movieapp.domain.utils.Resource
import com.example.movieapp.domain.utils.extensions.setImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment :
    BaseFragment<MovieDetailsFragmentBinding>(MovieDetailsFragmentBinding::inflate) {

    private val viewModel: MovieDetailsViewModel by viewModel()

    private val safeArgs: MovieDetailsFragmentArgs by navArgs()

    private lateinit var adapter: ActorsAdapter

    override fun init() {
        checkInDb()
        initRecyclerView()
        initListeners()
        if (isOnline(requireContext())) {
            getMovieDetails()
            observeWholeData()
            observeActorsDetails()
            binding.save.isVisible = true
        } else if (!isOnline(requireContext())) {
            observeCheckDb()
        }
    }

    private fun checkInDb() {
        viewModel.checkMovieInDB(safeArgs.id)
    }

    private fun observeCheckDb() {
        viewModel.checkInDb.observe(viewLifecycleOwner) {
            if (it) {
                getMovieDetailsFromDB()
                binding.save.isClickable = false
            } else
                makeToastMessage(getString(R.string.cantLoad))
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = ActorsAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.watchLater.setOnClickListener {
            val action =
                MovieDetailsFragmentDirections
                    .actionMovieDetailsFragmentToItemListDialogFragment(
                        binding.titleTV.text.toString(),
                        safeArgs.id
                    )
            findNavController().navigate(action)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.save.setOnClickListener {
            when (viewModel.movieDetails.value) {
                is Resource.Success -> {
                    viewModel.saveMovie((viewModel.movieDetails.value as Resource.Success<MovieDetailsModel>).data)
                    binding.save.isClickable = false
                    makeToastMessage(getString(R.string.addedToDB))
                }
                is Resource.Error -> {
                    makeToastMessage(getString(R.string.cantSave))
                    binding.save.isClickable = false
                }
                is Resource.Loading -> {
                    makeToastMessage(getString(R.string.pleaseWait))
                }
                is Resource.Idle -> {
                    makeToastMessage(getString(R.string.pleaseWait))
                }
            }
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

    private fun getMovieDetailsFromDB() {
        viewModel.getMovieById(safeArgs.id)
        viewModel.movieDetailsFromDb.observe(viewLifecycleOwner) {
            successObserve(it)
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
                    it.message?.let { it1 -> makeToastMessage(it1) }
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