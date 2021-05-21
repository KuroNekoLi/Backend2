package com.cmoney.backend2.activity.service.api.requestbonus


import com.google.gson.annotations.SerializedName

data class RequestBonusRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ReferrerId")
    val referrerId: Long,
    @SerializedName("EventId")
    val eventId: Long
)