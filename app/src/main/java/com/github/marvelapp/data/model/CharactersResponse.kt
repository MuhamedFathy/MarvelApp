package com.github.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("code") val code: Long? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("copyright") val copyright: String? = null,
    @SerializedName("attributionText") val attributionText: String? = null,
    @SerializedName("attributionHTML") val attributionHTML: String? = null,
    @SerializedName("data") val data: Data? = null
)

data class Data(
    @SerializedName("offset") val offset: Long? = null,
    @SerializedName("limit") val limit: Long? = null,
    @SerializedName("total") val total: Long? = null,
    @SerializedName("count") val count: Long? = null,
    @SerializedName("results") val characters: List<CharacterResponse>? = null
)

data class CharacterResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("modified") val modified: String? = null,
    @SerializedName("thumbnail") val thumbnail: Thumbnail? = null,
    @SerializedName("resourceURI") val resourceURI: String? = null,
    @SerializedName("urls") val urls: List<Url>? = null
)

data class Thumbnail(
    @SerializedName("path") val path: String? = null,
    @SerializedName("extension") val extension: String? = null
)

data class Url(
    @SerializedName("type") val type: String? = null,
    @SerializedName("url") val url: String? = null
)
