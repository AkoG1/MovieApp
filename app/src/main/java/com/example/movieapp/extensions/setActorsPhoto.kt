package com.example.movieapp.extensions

import android.widget.ImageView
import com.example.movieapp.R
import com.squareup.picasso.Picasso

fun ImageView.setActorImage(url: String?) {

    if (!url.isNullOrEmpty()) {
        Picasso.get().load("https://image.tmdb.org/t/p/original$url").into(this)
    } else {
        setImageResource(R.mipmap.ic_launcher)
    }

}