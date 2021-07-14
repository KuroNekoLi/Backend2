package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

enum class OceanChannelType(val value : Int) {
    //0',  '102',  '301',, '402', '501',, '9999'

    /**
     * 會員頻道
     */
    @SerializedName("101")
    Member(101),

    /**
     * 會員建立的社團頻道
     */
    @SerializedName("201")
    MemberClub(201),

    /**
     * 理財寶社團頻道
     */
    @SerializedName("401")
    OfficialClub(401),

    /**
     * 訊號頻道
     */
    @SerializedName("601")
    Signal(601),

    /**
     * RSS新聞頻道
     */
    @SerializedName("701")
    RssNews(701)


}