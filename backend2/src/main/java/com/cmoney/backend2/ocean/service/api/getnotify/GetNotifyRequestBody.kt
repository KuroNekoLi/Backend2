package com.cmoney.backend2.ocean.service.api.getnotify
import com.google.gson.annotations.SerializedName


data class GetNotifyRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("IsIncludeClub")
    val isIncludeClub: Boolean,
    @SerializedName("UpperBoundNotifyTime")
    val upperBoundNotifyTime: Long,
    @SerializedName("NotifyTypes")
    val notifyTypes: List<Int>,
    @SerializedName("FetchCount")
    val fetchCount : Int
)