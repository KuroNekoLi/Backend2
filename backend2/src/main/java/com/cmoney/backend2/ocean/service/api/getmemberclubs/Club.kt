package com.cmoney.backend2.ocean.service.api.getmemberclubs

import com.cmoney.backend2.ocean.service.api.variable.ClubInfo
import com.cmoney.backend2.ocean.service.api.variable.MemberClubInfo
import com.google.gson.annotations.SerializedName

/**
 * 社團基本資料
 */
data class Club (
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
    val memberClubInfo: MemberClubInfo?,
    @SerializedName("ViewerClubInfo")
    val viewerClubInfo: MemberClubInfo?
)