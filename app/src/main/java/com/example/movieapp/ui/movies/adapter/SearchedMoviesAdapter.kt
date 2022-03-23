package com.example.movieapp.ui.movies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MoviesRecyclerItemBinding
import com.example.movieapp.extensions.setImage
import com.example.movieapp.model.MovieDetailsModel

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
                    model.imdbID?.let { it1 -> onMovieClick.invoke(it1) }
                }
                if (!model.genre.isNullOrEmpty()) {
                    val genre = model.genre.split(",").map { it.trim() }
                    Log.d("genreSize", "onBind: ${genre.size}")
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

    fun setData(searchedMovies: MutableList<MovieDetailsModel>) {
        this.searchedMovies.clear()
        this.searchedMovies.addAll(searchedMovies)
        notifyDataSetChanged()
    }

}