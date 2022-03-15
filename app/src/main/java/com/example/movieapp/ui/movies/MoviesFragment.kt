package com.example.movieapp.ui.movies

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.ui.base.BaseFragment
import com.example.movieapp.ui.movies.adapter.SearchedMoviesAdapter
import com.example.movieapp.ui.movies.vm.MoviesViewModel
import com.example.movieapp.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModel()

    private lateinit var adapter: SearchedMoviesAdapter

    override fun init() {
        swipeRefreshListener()
        initSearchBar()
        initRecyclerView()
    }

    private fun swipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            val lastSearchedText = binding.searchBar.text.toString()
            viewModel.swipeRefresh(lastSearchedText)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initSearchBar() {
        binding.searchBar.addTextChangedListener {
            requestMovies(it.toString())
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchedMoviesAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun requestMovies(searchedText: String) {
        viewModel.getSearchedMovies(searchedText = searchedText)
        observe()
    }

    private fun observe() {
        viewModel.searchedMovies.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = true
                    it.data.search?.let { it1 -> adapter.setData(it1) }
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = true
                    it.message?.let { it1 -> makeToastMessage(it1) }
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Idle -> {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }
}