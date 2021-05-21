package com.cmoney.backend2.media.service.api.getpaidlivelist


import com.cmoney.backend2.media.service.api.getpaidmedialist.MediaTagInfo
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class PaidLiveListInfo (
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
     * 在線人數(付費直播)
     */
    @SerializedName("OnlineCount")
    val onlineCount: Int?,


    /**
     * 該使用者有沒有權限
     */
    @SerializedName("HasAuth")
    val hasAuth: Boolean?,


    /**
     * 該影片開始時間
     */
    @SerializedName("StartTime")
    val startTime: Long?,

    /**
     * 結束時間
     */
    @SerializedName("EndTime")
    val endTime: Long?,

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