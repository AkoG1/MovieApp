package com.example.movieapp.ui.movieDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ActorsItemBinding
import com.example.movieapp.extensions.setImage
import com.example.movieapp.model.ActorsModel

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {

    private val actorsDetailsList = mutableListOf<ActorsModel>()

    inner class ViewHolder(private val binding: ActorsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = actorsDetailsList[position]
            with(binding) {
                if (model.results != null && model.results.isNotEmpty()) {
                    nameTV.text = model.results[0].name.toString()
                    actorsPhoto.setImage("https://image.tmdb.org/t/p/original" + model.results[0].profilePath.toString())
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

    fun setData(actorsDetails: List<ActorsModel>) {
        this.actorsDetailsList.clear()
        this.actorsDetailsList.addAll(actorsDetails)
        notifyDataSetChanged()
    }

}