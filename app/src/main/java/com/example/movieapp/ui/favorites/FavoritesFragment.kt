package com.example.movieapp.ui.favorites

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentFavoritesBinding
import com.example.movieapp.room.entity.MovieEntity
import com.example.movieapp.ui.base.BaseFragment
import com.example.movieapp.ui.favorites.adapter.SavedMoviesAdapter
import com.example.movieapp.ui.favorites.vm.FavoritesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding:: inflate) {

    private lateinit var adapter: SavedMoviesAdapter

    private val viewModel: FavoritesViewModel by viewModel()

    override fun init() {
        initRecyclerView()
        observe()
    }

    private fun observe() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = SavedMoviesAdapter(::onRemoveClicked, ::onClicked)
        binding.recyclerView.adapter = adapter
    }

    private fun onRemoveClicked(movieEntity: MovieEntity) {
        viewModel.deleteMovieFromFav(movieEntity)
    }

    private fun onClicked(movieEntity: MovieEntity) {
        val action = FavoritesFragmentDirections.actionNavigationNotificationsToMovieDetailsFragment(movieEntity.imdbID)
        findNavController().navigate(action)
    }

}