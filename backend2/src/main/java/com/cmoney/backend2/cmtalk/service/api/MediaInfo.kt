package com.cmoney.backend2.cmtalk.service.api

import com.google.gson.annotations.SerializedName

/**
 * @property mediaId 影音ID
 * @property title 房間名稱
 * @property subtitle 副標題
 * @property createTime 建立時間
 * @property url 影片位址
 * @property thumbnailUrl 縮圖位址
 * @property viewCount 觀看人數，瀏覽人次
 * @property duration 影片長度，格式: 1:52:30
 */
data class MediaInfo(

    @SerializedName("Id")
    val mediaId: Int?,

    @SerializedName("Title")
    val title: String?,

    @SerializedName("Subtitle")
    val subtitle: String?,

    @SerializedName("CreateTime")
    val createTime: Long?,

    @SerializedName("Url")
    val url: String?,

    @SerializedName("ThumbnailUrl")
    val thumbnailUrl: String?,

    @SerializedName("ViewCount")
    val viewCount: Int?,

    @SerializedName("Duration")
    val duration: String?
)