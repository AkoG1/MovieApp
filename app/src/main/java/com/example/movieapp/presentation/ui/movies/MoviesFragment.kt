package com.example.movieapp.presentation.ui.movies

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.presentation.base.BaseFragment
import com.example.movieapp.presentation.ui.movies.adapter.SearchedMoviesAdapter
import com.example.movieapp.domain.utils.Resource
import com.example.movieapp.presentation.extensions.onTextChangedListener
import com.example.movieapp.presentation.ui.movies.vm.MoviesViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    @OptIn(FlowPreview::class)
    private fun initSearchBar() {
        binding.searchBar.onTextChangedListener(MINIMUM_CHARS).debounce(1000).onEach {
            val text = binding.searchBar.text.toString()
            requestMovies(text)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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
                else -> {
                    makeToastMessage(getString(R.string.unknownError))
                }
            }
        }
    }

    companion object {
        private const val MINIMUM_CHARS = 4
    }
}