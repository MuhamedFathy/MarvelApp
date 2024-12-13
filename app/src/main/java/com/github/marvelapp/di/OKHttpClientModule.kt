package com.github.marvelapp.di

import com.github.marvelapp.BuildConfig
import com.github.marvelapp.data.network.interceptor.MarvelApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OKHttpClientModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        marvelApiInterceptor: MarvelApiInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = Builder()
        .connectTimeout(60, SECONDS)
        .readTimeout(60, SECONDS)
        .writeTimeout(60, SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(marvelApiInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE) }
}
