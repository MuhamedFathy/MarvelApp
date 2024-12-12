package com.github.marvelapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.marvelapp.data.model.mapper.toEntity
import com.github.marvelapp.data.network.api.MarvelAPI
import com.github.marvelapp.data.source.MarvelCharactersPagingSource
import com.github.marvelapp.domain.entity.CharacterEntity
import com.github.marvelapp.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarvelCharactersRepositoryImpl @Inject constructor(
    private val marvelAPI: MarvelAPI
) : MarvelCharactersRepository {

    override suspend fun getAllCharacters(): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 4,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MarvelCharactersPagingSource(marvelAPI) }
        )
            .flow
            .catch { it.printStackTrace() }
            .map { pagingData -> pagingData.map { it.toEntity() } }
    }
}
