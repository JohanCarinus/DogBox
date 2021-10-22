package com.example.dogbox.di

import com.example.dogbox.repository.DogsRepository
import com.example.dogbox.repository.DogsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideDogsRepository(): DogsRepository {
        return DogsRepositoryImpl()
    }
}