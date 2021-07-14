package com.cmoney.backend2.ocean.service.api.setreaded
import com.google.gson.annotations.SerializedName


data class SetReadedRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("NotifyIdAndIsSpecificPair")
    val notifyIdAndIsSpecificPair: List<NotifyIdAndIsSpecificPair>
)

data class NotifyIdAndIsSpecificPair(
    @SerializedName("IsSpecific")
    val isSpecific: Boolean,
    @SerializedName("NotifyId")
    val notifyId: Long
)