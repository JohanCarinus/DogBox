package com.example.dogbox.ui.adapter

import android.net.Uri

class MasonryImageGalleryOnClickListener (val callback: (uri:Uri) -> Unit) {
    fun onClick(uri: Uri) = callback(uri)
}