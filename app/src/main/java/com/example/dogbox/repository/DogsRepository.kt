package com.example.dogbox.repository

import android.net.Uri

interface DogsRepository {
    fun getDogUrls(breed: String = "ALL"): List<Uri>
}