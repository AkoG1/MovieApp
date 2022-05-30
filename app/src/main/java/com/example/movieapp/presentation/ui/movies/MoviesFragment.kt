package com.example.movieapp.presentation.ui.movies

import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.domain.utils.Resource
import com.example.movieapp.presentation.base.BaseFragment
import com.example.movieapp.presentation.ui.movies.adapter.SearchedMoviesAdapter
import com.example.movieapp.presentation.ui.movies.vm.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModel()

    private lateinit var adapter: SearchedMoviesAdapter

    override fun init() {
        swipeRefreshListener()
        initActorsRecyclerView()
        initSearchBar()
        observe()
    }

    private fun swipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            val lastSearchedText = binding.searchBar.text.toString()
            viewModel.swipeRefresh(lastSearchedText)
        }
    }

    private fun onMovieClick(id: String) {
        val action = MoviesFragmentDirections.actionNavigationHomeToMovieDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun initSearchBar() {
        val searchBarTextWatcher: TextWatcher = object : TextWatcher {
            private var timer = Timer()
            private val DELAY: Long = 1000L

            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        if (binding.searchBar.text?.length!! >= MINIMUM_CHARS)
                            requestMovies(binding.searchBar.text.toString())
                    }
                }, DELAY)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }
        binding.searchBar.addTextChangedListener(searchBarTextWatcher)
    }

    private fun initActorsRecyclerView() {
        binding.actorsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchedMoviesAdapter(::onMovieClick)
        binding.actorsRecyclerView.adapter = adapter
    }

    private fun requestMovies(searchedText: String) {
        viewModel.getSearchedMovies(searchedText = searchedText)
    }

    private fun observe() {
        viewModel.searchedMovies.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.submitList(it.data)
                    isProgressBarVisible(false)
                }
                is Resource.Error -> {
                    isProgressBarVisible(false)
                }
                is Resource.Loading -> {
                    isProgressBarVisible(true)
                }
                is Resource.Idle -> {
                    isProgressBarVisible(false)
                }
                else -> {
                    makeToastMessage(getString(R.string.unknownError))
                }
            }
        }
    }

    private fun isProgressBarVisible(boolean: Boolean) {
        binding.swipeRefresh.isRefreshing = boolean
    }

    companion object {
        private const val MINIMUM_CHARS = 4
    }
}