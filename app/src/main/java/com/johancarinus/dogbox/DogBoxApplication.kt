package com.johancarinus.dogbox

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import johancarinus.dogbox.R

@HiltAndroidApp
class DogBoxApplication : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .placeholder(R.drawable.placeholder)
            .build()
    }
}