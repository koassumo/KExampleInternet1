package com.example.kexampleinternet1.ui

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kexampleinternet1.model.IGladeImageLoader


class GladeImageLoader: IGladeImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container )
    }

}