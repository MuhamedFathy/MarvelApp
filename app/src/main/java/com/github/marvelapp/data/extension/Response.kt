package com.github.marvelapp.data.extension

import com.github.marvelapp.domain.exception.DataRetrievingFailException
import com.github.marvelapp.domain.holder.DataHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T, R> Response<T>.toFlow(
    mapBody: (T?) -> R,
    mapError: (Response<T>) -> Throwable = { DataRetrievingFailException() }
): Flow<DataHolder<R>> = flow {
    try {
        if (this@toFlow.isSuccessful) {
            val body = this@toFlow.body()
            val mappedData = mapBody(body)
            @Suppress("UNCHECKED_CAST")
            emit(DataHolder.Success(mappedData) as DataHolder<R>)
        } else {
            emit(DataHolder.Fail(mapError(this@toFlow)))
        }
    } catch (e: Exception) {
        emit(DataHolder.Fail(e))
    }
}.flowOn(Dispatchers.IO)
