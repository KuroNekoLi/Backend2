package com.cmoney.backend2.sample.extension

import android.util.Log

inline fun <reified T> Result<T>.logResponse(tag: String) {
    this.fold(
        {
            Log.d(tag, "response: $it")
        },
        {
            it.printStackTrace()
        }
    )
}

inline fun <reified T>Result<T>.logResponse(tag: String,successCallback : (T)-> Unit) {
    this.fold(
        {
            successCallback.invoke(it)
            Log.d(tag, "response: $it")
        },
        {
            it.printStackTrace()
        }
    )
}