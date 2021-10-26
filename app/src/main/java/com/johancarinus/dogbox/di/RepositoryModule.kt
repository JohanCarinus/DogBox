package com.johancarinus.dogbox.di

import com.johancarinus.dogbox.repository.DogsRepository
import com.johancarinus.dogbox.repository.DogsRepositoryImpl
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