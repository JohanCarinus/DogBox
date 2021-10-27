package com.johancarinus.dogbox.service

import com.johancarinus.dogbox.model.service.ImageListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {

    companion object {
        const val BASE_URL = "https://dog.ceo/api/"
    }

    @GET("breeds/image/random/{numberOfDogs}")
    suspend fun getRandomImages(@Path("numberOfDogs") numberOfDogs: Int): ImageListResponse
}