package com.cmoney.backend2.mobileocean.service.api.common.article

import com.google.gson.annotations.SerializedName

/**
 * 所在頻道資訊，目前只有社團內的文章會有此資訊  (因為社團文章可能是 在社團中以 社團名義發文  與在社團中 以用戶名義發文  因此為了確認都是社團文章 以此類別來區分)
 */
data class LocationChannelInfo(

    /**
     * 文章編號
     */
    @SerializedName("ChannelId")
    val channelId : Long?,

    /**
     * 頻道類型
     * 101:會員頻道
     * 601:訊號頻道
     * 701:RSS新聞頻道
     * 201:會員建立的社團頻道
     * 401:理財寶社團頻道
     */
    @SerializedName("ChannelType")
    val channelType : ChannelType?,

    /**
     * 頻道名稱
     */
    @SerializedName("Name")
    val name :String?
)