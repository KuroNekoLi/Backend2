package com.cmoney.backend2.frontendlogger.service.api

import com.google.gson.annotations.SerializedName

class LogRequestBody(
    @SerializedName("request")
    val request: LogRequest,
    @SerializedName("response")
    val response: LogResponse,
    @SerializedName("additionalInfo")
    val additionalInfo: Map<String, String>?,
    @SerializedName("message")
    val message: String?,
)
