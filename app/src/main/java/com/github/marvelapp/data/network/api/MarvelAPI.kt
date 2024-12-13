package com.github.marvelapp.data.network.api

import com.github.marvelapp.data.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelAPI {

    @GET("characters")
    suspend fun getAllCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<CharactersResponse>

    @GET("characters/{characterId}/{type}")
    suspend fun getCharacterProducts(
        @Path("characterId") characterId: Long,
        @Path("type") type: String,
    ): Response<CharactersResponse>
}
