package com.cmoney.backend2.ocean.service.api.getunreadcount
import com.google.gson.annotations.SerializedName


data class GetUnreadCountRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("IsIncludeClub")
    val isIncludeClub: Boolean,
    @SerializedName("LowerBoundNotifyTime")
    val lowerBoundNotifyTime: Long,
    @SerializedName("NotifyTypes")
    val notifyTypesList : List<Int>
)