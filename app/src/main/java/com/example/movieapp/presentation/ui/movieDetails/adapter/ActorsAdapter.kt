package com.example.movieapp.presentation.ui.movieDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ActorsItemBinding
import com.example.movieapp.domain.model.ActorsModel
import com.example.movieapp.domain.utils.extensions.setImage

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {

    private val actorsDetailsList = mutableListOf<ActorsModel>()

    inner class ViewHolder(private val binding: ActorsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = actorsDetailsList[position]
            with(binding) {
                if (model.result != null && model.result.isNotEmpty()) {
                    nameTV.text = model.result[ZERO].name.toString()
                    actorsPhoto.setImage(BASE_IMG_URL + model.result[ZERO].profilePath.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ActorsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = actorsDetailsList.size

    fun setData(actorsDetailDtos: List<ActorsModel>) {
        this.actorsDetailsList.clear()
        this.actorsDetailsList.addAll(actorsDetailDtos)
        notifyDataSetChanged()
    }

    companion object {
        private const val BASE_IMG_URL = "https://image.tmdb.org/t/p/original"
        private const val ZERO = 0
    }

}