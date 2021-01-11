package com.example.taipeizooinfo.presentation.util

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.taipeizooinfo.R

class GlideManagerImpl(
    private val requestManager:RequestManager
):GlideManager {
    override fun setImage(imageUrl: String?, imageView: ImageView) {
        requestManager
            .load(imageUrl)
            .fitCenter()
            .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
            .error(R.drawable.taipei_zoo)
            .into(imageView)
    }


}
