package com.cmoney.backend2.media.service.api.getmediainfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class MediaInfoWithError(
    @SerializedName("Id")
    val id: Long? = 0L,
    @SerializedName("Title")
    val title: String? = "",
    @SerializedName("OnlineCount")
    val onlineCount: Int? = 0,
    /**
     * 影片描述
     */
    @SerializedName("Description")
    val description: String? = "",
    /**
     * 影片課程簡介
     */
    @SerializedName("MediaOverview")
    val mediaOverview: String? = "",
    @SerializedName("OriginalPrice")
    val originalPrice: Float? = 0.0f,
    /**
     * 優惠價
     */
    @SerializedName("Price")
    val price: Float? = 0.0f,
    @SerializedName("PreviewImageUrl")
    val previewImageUrl: String? = "",
    @SerializedName("StartTime")
    val startTime: Long? = 0L,
    @SerializedName("EndTime")
    val endTime: Long? = 0L,
    @SerializedName("AuthorName")
    val authorName: String? = "",
    /**
     * 講義網址
     */
    @SerializedName("DownloadFileUrl")
    val downloadFileUrl: String? = "",
    /**
     * 講義網址
     */
    @SerializedName("RoomId")
    val roomId: Int? = 0,
    @SerializedName("PurchaseCount")
    val purchaseCount: Int? = 0,
    @SerializedName("HasAuth")
    val hasAuth: Boolean? = false,
    /**
     * 直播:3 普通影音:不等於3
     */
    @SerializedName("MediaType")
    val mediaType: Int? = 0
) : CMoneyError(), IWithError<MediaInfo> {
    override fun toRealResponse(): MediaInfo {
        return MediaInfo(
            id,
            title,
            onlineCount,
            description,
            mediaOverview,
            originalPrice,
            price,
            previewImageUrl,
            startTime,
            endTime,
            authorName,
            downloadFileUrl,
            roomId,
            purchaseCount,
            hasAuth,
            mediaType
        )
    }
}