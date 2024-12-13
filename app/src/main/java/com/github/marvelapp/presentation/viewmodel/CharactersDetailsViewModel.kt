package com.github.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.marvelapp.domain.entity.CharacterEntity
import com.github.marvelapp.domain.holder.DataHolder
import com.github.marvelapp.domain.interactor.GetMarvelCharacterProductUseCase
import com.github.marvelapp.presentation.viewmodel.uimodel.CharacterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersDetailsViewModel @Inject constructor(
    private val getMarvelCharacterProductUseCase: GetMarvelCharacterProductUseCase
) : ViewModel() {

    private val _comicsDataState = MutableStateFlow<DataHolder<List<CharacterEntity>?>>(value = DataHolder.Loading)
    val comicsDataState = _comicsDataState.asStateFlow()

    private val _seriesDataState = MutableStateFlow<DataHolder<List<CharacterEntity>?>>(value = DataHolder.Loading)
    val seriesDataState = _seriesDataState.asStateFlow()

    private val _storiesDataState = MutableStateFlow<DataHolder<List<CharacterEntity>?>>(value = DataHolder.Loading)
    val storiesDataState = _storiesDataState.asStateFlow()

    private val _eventsDataState = MutableStateFlow<DataHolder<List<CharacterEntity>?>>(value = DataHolder.Loading)
    val eventsDataState = _eventsDataState.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Pair<List<CharacterUiModel>, Int>>(value = Pair(emptyList(), 0))
    val selectedProduct = _selectedProduct.asStateFlow()

    fun getCharacterComics(characterId: Long) {
        viewModelScope.launch {
            getMarvelCharacterProductUseCase.build(characterId, "comics")
                .collect { dataHolder ->
                    _comicsDataState.value = dataHolder
                }
        }
    }

    fun getCharacterSeries(characterId: Long) {
        viewModelScope.launch {
            getMarvelCharacterProductUseCase.build(characterId, "series")
                .collect { dataHolder ->
                    _seriesDataState.value = dataHolder
                }
        }
    }

    fun getCharacterStories(characterId: Long) {
        viewModelScope.launch {
            getMarvelCharacterProductUseCase.build(characterId, "stories")
                .collect { dataHolder ->
                    _storiesDataState.value = dataHolder
                }
        }
    }

    fun getCharacterEvents(characterId: Long) {
        viewModelScope.launch {
            getMarvelCharacterProductUseCase.build(characterId, "events")
                .collect { dataHolder ->
                    _eventsDataState.value = dataHolder
                }
        }
    }

    fun openSelectedProduct(products: List<CharacterUiModel>, selectedIndex: Int) {
        _selectedProduct.value = Pair(products, selectedIndex)
    }

    fun resetData() {
        _comicsDataState.value = DataHolder.Loading
        _seriesDataState.value = DataHolder.Loading
        _storiesDataState.value = DataHolder.Loading
        _eventsDataState.value = DataHolder.Loading
    }
}
