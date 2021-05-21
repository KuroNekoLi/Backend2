package com.cmoney.backend2.activity.service.api.getreferralcount


import com.google.gson.annotations.SerializedName

data class GetReferralCountRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("EventId")
    val eventId: Long
)