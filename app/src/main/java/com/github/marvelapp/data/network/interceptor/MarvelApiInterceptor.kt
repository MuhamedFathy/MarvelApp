package com.github.marvelapp.data.network.interceptor

import com.github.marvelapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date
import javax.inject.Inject

class MarvelApiInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val ts = Date().time.toString()
        val publicKey = BuildConfig.PUBLIC_API_KEY
        val privateKey = BuildConfig.PRIVATE_API_KEY
        val hash = md5("$ts$privateKey$publicKey")

        val urlWithParams = originalRequest.url.newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", hash)
            .build()

        return chain.proceed(originalRequest.newBuilder().url(urlWithParams).build())
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return BigInteger(1, digest).toString(16).padStart(32, '0')
    }
}
