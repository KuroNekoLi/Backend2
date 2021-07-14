package com.cmoney.backend2.mobileocean.service.api.common.article

import com.google.gson.annotations.SerializedName

/**
 * 頻道類型 (根據authorChannelId判斷\)
 */
enum class ChannelType(value: Int){
    /**
     * 會員文章
     */
    @SerializedName("101")
    MemberArticle(101),

    /**
     * 會員建立的社團頻道
     *
     */
    @SerializedName("201")
    ClubArticle(201),

    /**
     * 理財寶社團頻道
     *
     */
    @SerializedName("401")
    OfficialArticle(401),

    /**
     * 新聞文章
     */
    @SerializedName("701")
    NewsArticle(701),

    /**
     * 訊號文章
     */
    @SerializedName("601")
    SignalArticle(601);
}