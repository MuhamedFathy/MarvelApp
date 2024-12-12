package com.github.marvelapp.domain.repository

import androidx.paging.PagingData
import com.github.marvelapp.domain.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {

    suspend fun getAllCharacters(): Flow<PagingData<CharacterEntity>>
}
