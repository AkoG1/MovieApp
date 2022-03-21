package com.example.movieapp.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
                genreTV.text = model.genre
                itemView.setOnClickListener {
                    model.imdbID?.let { it1 -> onMovieClick.invoke(it1) }
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

    fun clearData() {
        this.searchedMovies.clear()
        notifyDataSetChanged()
    }

}