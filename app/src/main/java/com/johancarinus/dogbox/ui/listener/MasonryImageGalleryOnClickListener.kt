package com.johancarinus.dogbox.ui.listener

import android.net.Uri

class MasonryImageGalleryOnClickListener(val callback: (uri: Uri) -> Unit) {
    fun onClick(uri: Uri) = callback(uri)
}