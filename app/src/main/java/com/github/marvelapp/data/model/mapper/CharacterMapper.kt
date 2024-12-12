package com.github.marvelapp.data.model.mapper

import com.github.marvelapp.data.model.CharacterResponse
import com.github.marvelapp.data.model.Url
import com.github.marvelapp.domain.entity.CharacterEntity
import com.github.marvelapp.domain.entity.UrlEntity

fun CharacterResponse.toEntity() = CharacterEntity(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty(),
    thumbnail = "${thumbnail?.path.orEmpty().replace("http:", "https:")}/${"detail"}.${thumbnail?.extension.orEmpty()}",
    urls = urls?.map { it.toEntity() }.orEmpty(),
)

fun Url.toEntity() = UrlEntity(
    type = type.orEmpty(),
    url = url.orEmpty()
)