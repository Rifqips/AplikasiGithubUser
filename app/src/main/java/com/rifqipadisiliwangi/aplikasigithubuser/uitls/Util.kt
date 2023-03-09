package com.rifqipadisiliwangi.aplikasigithubuser.uitls

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rifqipadisiliwangi.aplikasigithubuser.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.avatar_placeholder)
        .into(this)
}