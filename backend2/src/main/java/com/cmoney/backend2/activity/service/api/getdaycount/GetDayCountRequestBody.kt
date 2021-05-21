package com.cmoney.backend2.activity.service.api.getdaycount

import com.google.gson.annotations.SerializedName

class GetDayCountRequestBody (
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)