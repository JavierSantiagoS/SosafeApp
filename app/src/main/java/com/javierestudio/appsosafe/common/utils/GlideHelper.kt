package com.javierestudio.appsosafe.common.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ViewTarget

object GlideHelper {

    fun getGlide(context: Context, urlImg: String, imageView: ImageView): ViewTarget<ImageView?, Drawable?> {
        return Glide.with(context)
            .load( urlImg)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    fun getGlide(fragment: Fragment, photoReference: String, imageView: ImageView): ViewTarget<ImageView?, Drawable?> {
        return Glide.with(fragment)
            .load(Constants.URL_BASE + Constants.URL_PHOTO + Constants.URL_API_KEY + Constants.URL_PHOTO_REFERENCE + photoReference + Constants.URL_PHOTO_WIDTH )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(imageView)
    }

    fun getGlideRoom(context: Context, photoReference: String, imageView: ImageView): ViewTarget<ImageView?, Drawable?> {
        return Glide.with(context)
            .load(Constants.URL_BASE + Constants.URL_PHOTO + Constants.URL_API_KEY + Constants.URL_PHOTO_REFERENCE + photoReference + Constants.URL_PHOTO_WIDTH )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(imageView)
    }
}