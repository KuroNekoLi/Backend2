package com.cmoney.backend2.ocean.service.api.getmasters


import com.google.gson.annotations.SerializedName

data class Master(
    /**
     * 達人頻道編號
     */
    @SerializedName("ChannelId")
    val channelId: Long?,

    /**
     * 7天鑽石增加數
     */
    @SerializedName("DiamondIncreaseCount")
    val diamondIncreaseCount: Int?,

    /**
     * 7天粉絲增加數
     */
    @SerializedName("FansIncreaseCount")
    val fansIncreaseCount: Int?,

    /**
     * 達人圖片
     */
    @SerializedName("Image")
    val image: String?,

    /**
     * 達人上次排名(上次沒有排名則為int.max 2147483647)
     */
    @SerializedName("LastRanking")
    val lastRanking: Int?,

    /**
     * 達人名稱
     */
    @SerializedName("Name")
    val name: String?,

    /**
     * 熱門值
     */
    @SerializedName("Popularity")
    val popularity: Double?,

    /**
     * 達人目前排名
     */
    @SerializedName("Ranking")
    val ranking: Int?,

    /**
     * 評價分數
     */
    @SerializedName("Score")
    val score: Double?
)