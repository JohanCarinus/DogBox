package com.johancarinus.dogbox.repository

import android.net.Uri

interface DogsRepository {
    suspend fun getDogUrls(): List<Uri>
}