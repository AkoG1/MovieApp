package com.example.movieapp.presentation.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MoviesRecyclerItemBinding
import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.utils.extensions.setImage

class SearchedMoviesAdapter(private val onMovieClick: (id: String) -> Unit) :
    RecyclerView.Adapter<SearchedMoviesAdapter.ViewHolder>() {

    private val searchedMovies = mutableListOf<MovieDetailsModel>()

    inner class ViewHolder(private val binding: MoviesRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = searchedMovies[position]
            with(binding) {
                titleTV.text = model.title
                coverIV.setImage(model.poster)
                runtimeTV.text = model.runtime
                imdbRating.text = model.imdbRating
                itemView.setOnClickListener {
                    model.imdbID.let { it1 -> onMovieClick.invoke(it1) }
                }
                if (!model.genre.isNullOrEmpty()) {
                    val genre = model.genre.split(COMMA).map { it.trim() }
                    when (genre.size) {
                        ZERO -> {}
                        ONE -> genreTV.text = genre[ZERO]
                        TWO -> {
                            genreTV.text = genre[ZERO]
                            genre2TV.isVisible = true
                            genre2TV.text = genre[ONE]
                        }
                        THREE -> {
                            genreTV.text = genre[ZERO]
                            genre2TV.isVisible = true
                            genre2TV.text = genre[ONE]
                            genre3TV.isVisible = true
                            genre3TV.text = genre[TWO]
                        }
                    }
                }else {
                    binding.genreTV.visibility = View.INVISIBLE
                    binding.genre2TV.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MoviesRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = searchedMovies.size

    fun setData(searchedMovies: List<MovieDetailsModel>) {
        this.searchedMovies.clear()
        this.searchedMovies.addAll(searchedMovies)
        notifyDataSetChanged()
    }

    companion object {
        private const val COMMA = ","
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
    }

}