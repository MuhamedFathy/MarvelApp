package com.github.marvelapp.di

import com.github.marvelapp.data.network.api.MarvelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    @Singleton
    @Provides
    fun provideMarvelApi(retrofit: Retrofit): MarvelAPI = retrofit.create(MarvelAPI::class.java)
}
