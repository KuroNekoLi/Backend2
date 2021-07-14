package com.cmoney.backend2.mobileocean.service.api.getarticletips

import com.google.gson.annotations.SerializedName

data class Tip(
    /**
     * 會員頻道編號
     */
    @SerializedName("MemberChannelId")
    val memberChannelId : Long?,
    /**
     * 會員暱稱
     */
    @SerializedName("MemberNickName")
    val memberNickName : String?,
    /**
     * 達人頭像
     */
    @SerializedName("MemberImage")
    val memberImage : String?,
    /**
     * 打賞總金額
     */
    @SerializedName("TotalTip")
    val totalTip : Int?
)