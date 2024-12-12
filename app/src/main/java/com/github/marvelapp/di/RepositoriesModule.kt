package com.github.marvelapp.di

import com.github.marvelapp.data.repository.MarvelCharactersRepositoryImpl
import com.github.marvelapp.domain.repository.MarvelCharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindMarvelCharactersRepository(
        marvelCharactersRepositoryImpl: MarvelCharactersRepositoryImpl
    ): MarvelCharactersRepository
}
