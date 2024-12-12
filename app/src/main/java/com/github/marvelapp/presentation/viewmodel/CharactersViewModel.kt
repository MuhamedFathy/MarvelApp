package com.github.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.github.marvelapp.domain.interactor.GetMarvelCharactersUseCase
import com.github.marvelapp.presentation.viewmodel.uimodel.CharacterUiModel
import com.github.marvelapp.presentation.viewmodel.uimodel.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getMarvelCharactersUseCase: GetMarvelCharactersUseCase
) : ViewModel() {

    private val _charactersPagingState = MutableStateFlow<PagingData<CharacterUiModel>>(value = PagingData.empty())
    val charactersPagingState = _charactersPagingState.asStateFlow()

    private var hasLoadedData = false

    fun getMarvelCharacters() {
        if (hasLoadedData) return
        hasLoadedData = true
        viewModelScope.launch {
            getMarvelCharactersUseCase.build()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _charactersPagingState.value = pagingData.map { it.toUiModel() }
                }
        }
    }
}
