package com.github.marvelapp.domain.interactor

import com.github.marvelapp.domain.repository.MarvelCharactersRepository
import javax.inject.Inject

class GetMarvelCharactersUseCase @Inject constructor(
    private val marvelCharactersRepository: MarvelCharactersRepository
) {

    suspend fun build() = marvelCharactersRepository.getAllCharacters()
}
