package com.cmoney.backend2.portal.service.api.getranking

import com.google.gson.annotations.SerializedName

data class GetRanking(
    @SerializedName("Image")
    val image: String?,
    @SerializedName("MemberGuid")
    val memberGuid: String?,
    @SerializedName("NickName")
    val nickName: String?,
    @SerializedName("OrderNumber")
    val orderNumber: Int?,
    @SerializedName("Prize")
    val prize: Int?
)