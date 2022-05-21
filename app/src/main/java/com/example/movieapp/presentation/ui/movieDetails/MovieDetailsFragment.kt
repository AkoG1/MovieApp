package com.example.movieapp.presentation.ui.movieDetails

import android.os.Build
import android.view.View
import android.widget.ProgressBar
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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
            getMovieDetails(safeArgs.id)
            observeWholeData()
            makeSaveButtonVisible()
        } else if (!isOnline(requireContext())) {
            observeCheckDb()
        }
    }

    private fun checkInDb() {
        viewModel.checkMovieInDB(safeArgs.id)
    }

    private fun initRecyclerView() {
        binding.actorsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = ActorsAdapter()
        binding.actorsRecyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.watchLater.setOnClickListener {
            val title = binding.titleTV.text
            if (!title.isNullOrEmpty() && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                val action =
                    MovieDetailsFragmentDirections
                        .actionMovieDetailsFragmentToItemListDialogFragment(
                            title.toString(),
                            safeArgs.id
                        )
                findNavController().navigate(action)
            } else {
                makeToastMessage(getString(R.string.warning))
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.save.setOnClickListener {
            saveInDb()
        }
    }

    private fun getMovieDetails(id: String) {
        viewModel.getMovieDetails(id)
    }

    private fun observeWholeData() {
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    successObserve(it.data)
                }
                is Resource.Error -> {
                    it.message?.let { message -> makeToastMessage(message) }
                    hideProgressBar(binding.mainProgressBar)
                }
                is Resource.Loading -> {
                    appearProgressBar(binding.mainProgressBar)
                }
                is Resource.Idle -> {}
                else -> {
                    makeToastMessage(getString(R.string.detailsNotAvailable))
                }
            }
        }
    }

    private fun makeSaveButtonVisible() {
        binding.save.isVisible = true
    }

    private fun observeCheckDb() {
        viewModel.checkInDb.observe(viewLifecycleOwner) {
            if (it) {
                getMovieDetailsFromDB()
                binding.save.isClickable = false
            } else
                detailsNotAvailable(getString(R.string.detailsNotAvailable))
        }
    }

    //end of init() functions

    private fun saveInDb() {
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
            else -> {makeToastMessage(getString(R.string.unknownError))}
        }
    }

    private fun successObserve(movieDetails: MovieDetailsModel) {
        with(binding) {
            titleTV.text = movieDetails.title
            imdbRating.text = movieDetails.imdbRating
            lengthTextView.text = movieDetails.runtime
            languageTextView.text = movieDetails.language
            ratingTextView.text = movieDetails.rated
            description.text = movieDetails.plot
            coverIV.setImage(movieDetails.poster)
            movieDetails.actors?.let { name ->
                getActorsDetails(name)
                observeActorsDetails()
            }
            if (!movieDetails.genre.isNullOrEmpty()) {
                successfulGenreObserve(movieDetails.genre, this)
            } else {
                hideAttributeAndMakeToast(binding.flexbox, getString(R.string.genresNotAvailable))
            }
            hideProgressBar(binding.mainProgressBar)
            movieDetails.type?.let { type ->
                getMovieTrailer(safeArgs.id, type)
                observeMovieTrailer()
            }
        }
    }

    private fun getMovieDetailsFromDB() {
        viewModel.getMovieById(safeArgs.id)
        viewModel.movieDetailsFromDb.observe(viewLifecycleOwner) {
            successObserve(it)
        }
    }

    private fun detailsNotAvailable(message: String) {
        makeToastMessage(message)
        with(binding) {
            save.isClickable = false
            watchLater.isClickable = false
            titleTV.text = getString(R.string.detailsNotAvailable)
        }
    }

    private fun observeMovieTrailer() {
        viewModel.movieTrailer.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    if (!it.data.result.isNullOrEmpty()) {
                        val list = it.data.result
                        for (i in list.indices) {
                            if (list[i].type == TRAILER) {
                                initYoutubePlayer(list[i].key!!)
                                break
                            } else if (i == list.lastIndex) {
                                tvSeriesTrailerNotAvailable(getString(R.string.trailerNotAvailable))
                            }
                        }
                    } else {
                        tvSeriesTrailerNotAvailable(getString(R.string.trailerNotAvailable))
                    }
                }
                is Resource.Error -> {
                    tvSeriesTrailerNotAvailable(it.message!!)
                }
                else -> {
                    tvSeriesTrailerNotAvailable(getString(R.string.trailerNotAvailable))
                }
            }
        }
    }

    private fun initYoutubePlayer(youtubeId: String) {
        lifecycle.addObserver(binding.ytVideoView)
        binding.ytVideoView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                binding.ytVideoView.visibility = View.VISIBLE
                youTubePlayer.cueVideo(youtubeId, ZERO.toFloat())
                binding.trailerProgressBar.visibility = View.GONE
            }
        }
        )
    }

    private fun getActorsDetails(actors: String) {
        viewModel.getActorsDetails(actors = actors)
    }

    private fun successfulGenreObserve(
        genres: String,
        movieDetailsFragmentBinding: MovieDetailsFragmentBinding,
    ) {
        with(movieDetailsFragmentBinding) {
            val genre = genres.split(COMMA).map { it.trim() }
            when (genre.size) {
                ZERO -> {}
                ONE -> genreTV.text = genre.first()
                TWO -> {
                    genreTV.text = genre.first()
                    genre2TV.isVisible = true
                    genre2TV.text = genre[ONE]
                }
                THREE -> {
                    genreTV.text = genre.first()
                    genre2TV.isVisible = true
                    genre2TV.text = genre[ONE]
                    genre3TV.isVisible = true
                    genre3TV.text = genre[TWO]
                }
            }
        }
    }

    private fun getMovieTrailer(imdbId: String, type: String) {
        if (type == MOVIE)
            viewModel.requestMovieTrailer(imdbId)
        else {
            tvSeriesTrailerNotAvailable(getString(R.string.onlyMovieHasTrailer))
        }
    }

    private fun tvSeriesTrailerNotAvailable(message: String) {
        binding.trailerHeader.text = message
        binding.trailerHeader.textSize = THIRTEEN
        binding.ytVideoView.visibility = View.GONE
        binding.trailerProgressBar.isVisible = false
    }

    private fun observeActorsDetails() {
        viewModel.actorsDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.setData(it.data)
                    hideProgressBar(binding.actorsProgressBar)
                }
                is Resource.Error -> {
                    it.message?.let { message -> makeToastMessage(message) }
                    hideProgressBar(binding.actorsProgressBar)
                }
                is Resource.Loading -> {
                    appearProgressBar(binding.actorsProgressBar)
                }
                is Resource.Idle -> {appearProgressBar(binding.actorsProgressBar)}
                else -> {
                    hideAttributeAndMakeToast(
                        binding.actorsRecyclerView,
                        getString(R.string.actorsNotAvailable)
                    )
                }
            }
        }
    }

    private fun hideAttributeAndMakeToast(attribute: View, message: String?) {
        attribute.visibility = View.GONE
        message?.let { makeToastMessage(message) }
    }

    private fun appearProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        binding.ytVideoView.release()
        super.onDestroyView()
    }

    companion object {
        private const val MOVIE = "movie"
        private const val TRAILER = "Trailer"
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
        private const val THIRTEEN = 13F
        private const val COMMA = ","
    }
}