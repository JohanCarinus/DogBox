package com.johancarinus.dogbox.repository

import com.johancarinus.dogbox.model.UriImageData

interface DogsRepository {
    suspend fun getDogUrls(breed: String = "ALL"): List<UriImageData>
}