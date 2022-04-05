package com.example.movieapp.presentation.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.FavoriteItemBinding
import com.example.movieapp.domain.utils.extensions.setImage
import com.example.movieapp.data.room.entity.MovieEntity

class SavedMoviesAdapter(
    private val onRemoveClicked: (movieEntity: MovieEntity) -> Unit,
    private val onClicked: (movieEntity: MovieEntity) -> Unit
) :
    RecyclerView.Adapter<SavedMoviesAdapter.ViewHolder>() {

    private val savedMoviesList = mutableListOf<MovieEntity>()

    inner class ViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = savedMoviesList[position]
            with(binding) {
                titleTV.text = model.title
                coverIV.setImage(model.poster)
                deleteFromFav.setOnClickListener {
                    onRemoveClicked.invoke(model)
                }
                coverIV.setOnClickListener {
                    onClicked.invoke(model)
                }
                titleTV.setOnClickListener {
                    onClicked.invoke(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = savedMoviesList.size

    fun setData(savedMovies: List<MovieEntity>) {
        this.savedMoviesList.clear()
        this.savedMoviesList.addAll(savedMovies)
        notifyDataSetChanged()
    }

}