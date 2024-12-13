package com.github.marvelapp.presentation.viewmodel.uimodel

import com.github.marvelapp.domain.entity.CharacterEntity
import com.github.marvelapp.domain.entity.UrlEntity

fun CharacterEntity.toUiModel() = CharacterUiModel(
    id = id,
    name = name,
    title = title,
    description = description,
    thumbnail = thumbnail,
    urls = urls.map { it.toUiModel() }
)

fun UrlEntity.toUiModel() = UrlUiModel(
    type = type,
    url = url
)
