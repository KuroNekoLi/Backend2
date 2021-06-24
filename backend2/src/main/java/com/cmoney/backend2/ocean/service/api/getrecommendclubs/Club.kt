package com.cmoney.backend2.ocean.service.api.getrecommendclubs

import com.cmoney.backend2.ocean.service.api.variable.ClubInfo
import com.cmoney.backend2.ocean.service.api.variable.MemberClubInfo
import com.google.gson.annotations.SerializedName

/**
 * 社團基本資料
 * 註:經Postman測試發現沒ViewerClubInfo回傳資料,所以拿掉ViewerClubInfo變數,原因請看RecommendClubsNeedInfo那邊
 */
data class Club(
    @SerializedName("ChannelId")
    val channelId: Int?,
    @SerializedName("ChannelName")
    val channelName: String?,
    @SerializedName("ClubInfo")
    val clubInfo: ClubInfo?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("MemberClubInfo")
    val memberClubInfo: MemberClubInfo?
)