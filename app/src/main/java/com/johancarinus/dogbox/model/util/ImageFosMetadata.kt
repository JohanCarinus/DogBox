package com.johancarinus.dogbox.model.util

import android.net.Uri
import java.io.Closeable
import java.io.OutputStream

data class ImageFosMetadata(val fos: OutputStream, val fileLocation: Uri) : Closeable {
    override fun close() {
        fos.flush()
        fos.close()
    }
}