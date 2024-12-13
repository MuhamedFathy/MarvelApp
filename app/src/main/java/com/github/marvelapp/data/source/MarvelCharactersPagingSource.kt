package com.github.marvelapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.marvelapp.data.model.CharacterResponse
import com.github.marvelapp.data.network.api.MarvelAPI
import com.github.marvelapp.domain.exception.DataRetrievingFailException

class MarvelCharactersPagingSource(
    private val marvelAPI: MarvelAPI
) : PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {
        val offset = params.key ?: STARTING_PAGE_OFFSET
        return try {
            val response = marvelAPI.getAllCharacters(offset = offset, limit = params.loadSize)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()
                LoadResult.Page(
                    data = data?.data?.characters.orEmpty(),
                    prevKey = null,
                    nextKey = if (data?.data?.characters.isNullOrEmpty()) null else offset.plus(params.loadSize)
                )
            } else {
                LoadResult.Error(DataRetrievingFailException())
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    companion object {

        private const val STARTING_PAGE_OFFSET = 0
    }
}
