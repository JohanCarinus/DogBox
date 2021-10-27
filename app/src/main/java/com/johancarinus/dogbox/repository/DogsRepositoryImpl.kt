package com.johancarinus.dogbox.repository

import android.net.Uri
import com.johancarinus.dogbox.service.DogApi

class DogsRepositoryImpl constructor(val dogApi: DogApi) : DogsRepository {

    val MAX_DOGS = 50
    val PAGE_SIZE = 20

    override suspend fun getDogUrls(): List<Uri> {
        val stringUriList = dogApi.getRandomImages(PAGE_SIZE).message
        val uriList = mutableListOf<Uri>()
        for (stringUri in stringUriList) {
            uriList.add(Uri.parse(stringUri))
        }
        return uriList.toList()
    }
}