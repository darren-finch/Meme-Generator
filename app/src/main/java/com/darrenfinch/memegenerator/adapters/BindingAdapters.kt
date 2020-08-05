package com.darrenfinch.memegenerator.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", "error")
fun loadImage(view: ImageView, imageUrl: String, error: Drawable) {
    //Remove weird backward slashes from the API
    val newImageUrl = imageUrl.replace("\\", "")
    Glide.with(view.context).load(newImageUrl).error(error).into(view)
}