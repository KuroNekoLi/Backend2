package com.cmoney.backend2.media.service.api.getpaidmedialistofmember

import com.google.gson.annotations.SerializedName

data class BoughtMediaListInfo (
    /**
     * 影片Id
     */
    @SerializedName("Id")
    val id :Long?,

    /**
     * 影片標題
     */
    @SerializedName("Title")
    val title:String?,

    /**
     * 預覽圖片
     */
    @SerializedName("PreviewImageUrl")
    val previewImageUrl:String?,

    /**
     * 開始時間
     */
    @SerializedName("StartTime")
    val startTime:Long?,

    /**
     * 結束時間
     */
    @SerializedName("EndTime")
    val endTime:Long?,

    /**
     * 影音類型 直播:3 普通影音:不等於3
     */
    @SerializedName("MediaType")
    val mediaType:Int?,

    /**
     * 該使用者的授權過期時間
     */
    @SerializedName("ExpireTime")
    val expireTime:Long?

)
