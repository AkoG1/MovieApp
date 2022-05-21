package com.example.movieapp.presentation.ui.movies

import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.presentation.base.BaseFragment
import com.example.movieapp.presentation.ui.movies.adapter.SearchedMoviesAdapter
import com.example.movieapp.domain.utils.Resource
import com.example.movieapp.presentation.ui.movies.vm.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        binding.searchBar.addTextChangedListener {
            if (it.toString().isNotEmpty() && it.toString().length >= MINIMUM_CHARS) {
                requestMovies(it.toString())
            } else
                viewModel.clearLiveData()
        }
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
                    adapter.setData(it.data)
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Idle -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                else -> {makeToastMessage(getString(R.string.unknownError))}
            }
        }
    }

    companion object {
        private const val MINIMUM_CHARS = 4
    }
}