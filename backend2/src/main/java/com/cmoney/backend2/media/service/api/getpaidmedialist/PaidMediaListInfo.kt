package com.cmoney.backend2.media.service.api.getpaidmedialist

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class PaidMediaListInfo(

    /**
     * 影片Id
     */
    @SerializedName("Id")
    val id: Long?,


    /**
     * 售價
     */
    @SerializedName("Price")
    val price: BigDecimal?,

    /**
     * 預覽圖片
     */
    @SerializedName("PreviewImageUrl")
    val previewImageUrl: String?,

    /**
     * 影片標題
     */
    @SerializedName("Title")
    val title: String?,

    /**
     * 觀看次數(付費影音)
     */
    @SerializedName("ViewCount")
    val viewCount: Int?,

    /**
     * 該使用者有沒有權限
     */
    @SerializedName("HasAuth")
    val hasAuth: Boolean?,

    /**
     * 播放長度
     */
    @SerializedName("ReleaseTimeMills")
    val releaseTimeMills: Long?,

    /**
     * 開始時間
     */
    @SerializedName("StartTime")
    val startTime: Long?,

    /**
     * 結束時間
     */
    @SerializedName("ExpireTime")
    val expireTime: Long?,

    /**
     * 講師名稱
     */
    @SerializedName("AuthorName")
    val authorName: String?,

    /**
     * 標籤清單
     */
    @SerializedName("TagList")
    val tagList: List<MediaTagInfo>?
)