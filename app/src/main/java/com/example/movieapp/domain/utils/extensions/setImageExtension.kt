package com.example.movieapp.domain.utils.extensions

import android.widget.ImageView
import com.example.movieapp.R
import com.squareup.picasso.Picasso

fun ImageView.setImage(url: String?) {

    if (!url.isNullOrEmpty()) {
        Picasso.get().load(url).into(this)
    } else {
        setImageResource(R.mipmap.ic_launcher)
    }

}