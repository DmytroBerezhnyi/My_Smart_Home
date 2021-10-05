package com.example.domain.extensions

import android.content.Context
import android.util.Base64

fun Context.readTextFromAsset(fileName : String) : String{
    return assets.open(fileName).bufferedReader().use {
        it.readText()}
}

fun ByteArray.toBase64(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP)
}

fun String.fromBase64(): ByteArray {
    return Base64.decode(toByteArray(), Base64.NO_WRAP)
}