package com.cmoney.backend2.vtwebapi.service.api.getattendgroup


import com.google.gson.annotations.SerializedName

/**
 * 競技場綁定的廣告資訊
 *
 * @property groupId 競技場編號
 * @property imageFileName 廣告圖檔名
 * @property positionNumber 廣告位置編號
 * @property url 廣告URL
 */
data class ArenaAdInfo(
    @SerializedName("groupId")
    val groupId: Long?,
    @SerializedName("imageFileName")
    val imageFileName: String?,
    @SerializedName("positionNumber")
    val positionNumber: Int?,
    @SerializedName("url")
    val url: String?
)