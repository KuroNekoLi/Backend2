package com.cmoney.backend2.base.model.request

import android.util.Base64

abstract class JwtToken<T>(val originContent: String) {
    fun getHeader(): String =
        String(Base64.decode(originContent.split(".").getOrNull(0).orEmpty(), Base64.URL_SAFE))

    abstract fun getPayload(): T

    fun getSignature(): String =
        String(Base64.decode(originContent.split(".").getOrNull(2).orEmpty(), Base64.URL_SAFE))

    open fun isEmpty() = originContent.isEmpty()
}