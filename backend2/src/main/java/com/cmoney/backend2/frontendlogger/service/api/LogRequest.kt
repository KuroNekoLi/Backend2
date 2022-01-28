package com.cmoney.backend2.frontendlogger.service.api

import com.google.gson.annotations.SerializedName

class LogRequest(
    @SerializedName("timeAt")
    val timeInMillis: Long,
    @SerializedName("method")
    val httpMethod: String?,
    @SerializedName("domain")
    val domain: String?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("header")
    val header: String?,
    @SerializedName("query")
    val query: String?,
    @SerializedName("body")
    val body: String?,
)
