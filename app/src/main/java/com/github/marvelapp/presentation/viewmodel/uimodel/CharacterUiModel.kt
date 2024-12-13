package com.github.marvelapp.presentation.viewmodel.uimodel

import androidx.compose.runtime.Immutable

@Immutable
data class CharacterUiModel(
    val id: Long,
    val name: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val urls: List<UrlUiModel>
)

@Immutable
data class UrlUiModel(
    val type: String,
    val url: String
)
