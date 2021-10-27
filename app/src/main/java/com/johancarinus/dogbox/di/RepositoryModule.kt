package com.johancarinus.dogbox.di

import com.johancarinus.dogbox.repository.DogsRepository
import com.johancarinus.dogbox.repository.DogsRepositoryImpl
import com.johancarinus.dogbox.service.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideDogsRepository(dogApi: DogApi): DogsRepository {
        return DogsRepositoryImpl(dogApi)
    }

    @Provides
    fun provideRetrofit(): DogApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DogApi.BASE_URL)
            .build()
        return retrofit.create(DogApi::class.java)
    }
}