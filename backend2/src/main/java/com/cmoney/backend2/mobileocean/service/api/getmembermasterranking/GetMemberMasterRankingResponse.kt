package com.cmoney.backend2.mobileocean.service.api.getmembermasterranking

import com.google.gson.annotations.SerializedName

data class GetMemberMasterRankingResponse(
    /**
     * 是否有排名
     */
    @SerializedName("HasRanking")
    val hasRanking : Boolean?,
    /**
     * 目前達人排名 (沒有排名，則為int最大值2147483647)
     */
    @SerializedName("Ranking")
    val ranking : Int?,
    /**
     * 是否有上次排名
     */
    @SerializedName("HasLastRanking")
    val hasLastRanking : Boolean?,
    /**
     * 上次排名(沒有排名，則為int最大值2147483647)
     */
    @SerializedName("LastRanking")
    val lastRanking : Int?,
    /**
     * 社群熱度
     */
    @SerializedName("Popularity")
    val popularity : Double?
)