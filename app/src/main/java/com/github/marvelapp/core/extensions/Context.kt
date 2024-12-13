package com.github.marvelapp.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrlInBrowser(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
