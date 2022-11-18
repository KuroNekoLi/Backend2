package com.cmoney.backend2.userbehavior.service.api.log

import com.cmoney.backend2.userbehavior.service.api.common.Event
import com.google.gson.annotations.SerializedName

data class LogRequestBody(
    @SerializedName("userEvents")
    val events: List<Event>,
    @SerializedName("processId")
    val serializedId: String?,
    @SerializedName("appId")
    val appId: Int,
    @SerializedName("platform")
    val platform: Int,
    @SerializedName("version")
    val version: String,
    @SerializedName("os")
    val os: String?,
    @SerializedName("device")
    val device: String?
)