package com.example.movieapp.presentation.ui.favorites

import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentFavoritesBinding
import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.presentation.base.BaseFragment
import com.example.movieapp.presentation.ui.favorites.adapter.SavedMoviesAdapter
import com.example.movieapp.presentation.ui.favorites.vm.FavoritesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private lateinit var adapter: SavedMoviesAdapter

    private val viewModel: FavoritesViewModel by viewModel()

    override fun init() {
        initRecyclerView()
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteMovies.collect {
                    if (it.isNotEmpty())
                        adapter.setData(it)
                    else
                        binding.nothingToSHow.isVisible = true
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.favoriteRecyclerView.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        adapter = SavedMoviesAdapter(::onRemoveClicked, ::onClicked)
        binding.favoriteRecyclerView.adapter = adapter
    }

    private fun onRemoveClicked(movieEntity: MovieEntity) {
        viewModel.deleteMovieFromFav(movieEntity)
    }

    private fun onClicked(movieEntity: MovieEntity) {
        val action =
            FavoritesFragmentDirections.actionNavigationNotificationsToMovieDetailsFragment(
                movieEntity.imdbID
            )
        findNavController().navigate(action)
    }

    companion object {
        private const val SPAN_COUNT = 2
    }

}