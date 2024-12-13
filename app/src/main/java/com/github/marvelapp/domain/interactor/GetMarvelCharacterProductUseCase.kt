package com.github.marvelapp.domain.interactor

import com.github.marvelapp.domain.repository.MarvelCharactersRepository
import javax.inject.Inject

class GetMarvelCharacterProductUseCase @Inject constructor(
    private val marvelCharactersRepository: MarvelCharactersRepository
) {

    suspend fun build(
        characterId: Long,
        type: String
    ) = marvelCharactersRepository.getCharacterProducts(characterId, type)
}
