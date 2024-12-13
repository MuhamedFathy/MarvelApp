package com.github.marvelapp.domain.holder

sealed class DataHolder<out T : Any?> {

    data object Loading : DataHolder<Nothing>()

    data class Success<out T : Any?>(val data: T?) : DataHolder<T?>()

    data class Fail(val e: Throwable) : DataHolder<Nothing>()
}
