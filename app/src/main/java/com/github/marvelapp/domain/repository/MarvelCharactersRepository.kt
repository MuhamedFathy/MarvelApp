package com.github.marvelapp.domain.repository

import androidx.paging.PagingData
import com.github.marvelapp.domain.entity.CharacterEntity
import com.github.marvelapp.domain.holder.DataHolder
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {

    suspend fun getAllCharacters(): Flow<PagingData<CharacterEntity>>
    suspend fun getCharacterProducts(characterId: Long, type: String): Flow<DataHolder<List<CharacterEntity>?>>
}
