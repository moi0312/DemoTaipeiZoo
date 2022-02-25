package com.edlo.mydemoapp.util

import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.edlo.mydemoapp.R


@GlideModule
class MyAppGlideModule: AppGlideModule()

var drawableCrossFadeFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun glideLoadUrl(url: String, imgView: ImageView) {
    GlideApp.with(imgView.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(drawableCrossFadeFactory))
        .placeholder(R.drawable.shape_thumb)
        .into(imgView)
}
