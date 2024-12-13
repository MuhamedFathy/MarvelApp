package com.github.marvelapp.domain.entity

data class CharacterEntity(
    val id: Long,
    val name: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val urls: List<UrlEntity>
)

data class UrlEntity(
    val type: String,
    val url: String
)
