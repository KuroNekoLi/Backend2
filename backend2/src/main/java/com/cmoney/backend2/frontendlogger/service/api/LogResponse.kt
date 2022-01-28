package com.cmoney.backend2.frontendlogger.service.api

import com.google.gson.annotations.SerializedName

class LogResponse(
    @SerializedName("timeAt")
    val timeInMillis: Long,
    @SerializedName("statusCode")
    val httpStatusCode: Int,
)
