package com.cmoney.backend2.cmtalk.service.api

import com.google.gson.annotations.SerializedName

/**
 * @property responseCode 回應狀態(預設為1)
 * @property responseMessage 回應訊息(預設為空字串)
 */
data class TargetMediaListInfo (

    @SerializedName("Media")
    val media: List<MediaInfo>?,

    @SerializedName("ResponseCode")
    val responseCode:Int?,

    @SerializedName("ResponseMsg")
    val responseMessage:String?
)