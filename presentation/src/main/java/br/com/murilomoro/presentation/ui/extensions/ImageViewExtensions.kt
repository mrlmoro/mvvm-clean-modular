package br.com.murilomoro.presentation.ui.extensions

import android.support.v7.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun AppCompatImageView.loadImageUrl(imageUrl: String) {
    Picasso.get()
        .load(imageUrl)
        .into(this)
}