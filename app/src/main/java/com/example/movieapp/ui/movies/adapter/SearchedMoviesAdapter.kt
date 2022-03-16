package com.example.movieapp.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MoviesRecyclerItemBinding
import com.example.movieapp.extensions.setImage
import com.example.movieapp.model.SearchedItemsModel

class SearchedMoviesAdapter(private val onMovieClick: (id: String) -> Unit) :
    RecyclerView.Adapter<SearchedMoviesAdapter.ViewHolder>() {

    private val searchedMovies = mutableListOf<SearchedItemsModel.Search>()

    inner class ViewHolder(private val binding: MoviesRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = searchedMovies[position]
            with(binding) {
                titleTV.text = model.title
                yearTV.text = model.year
                coverIV.setImage(model.poster)
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

    fun setData(searchedMovies: List<SearchedItemsModel.Search>) {
        this.searchedMovies.clear()
        this.searchedMovies.addAll(searchedMovies)
        notifyDataSetChanged()
    }

}