package com.cmoney.backend2.mobileocean.service.api.getmembermasterranking

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetMemberMasterRankingResponseWithError(
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
) : CMoneyError(), IWithError<GetMemberMasterRankingResponse> {
    override fun toRealResponse(): GetMemberMasterRankingResponse {
        return GetMemberMasterRankingResponse(hasRanking, ranking, hasLastRanking, lastRanking, popularity)
    }
}