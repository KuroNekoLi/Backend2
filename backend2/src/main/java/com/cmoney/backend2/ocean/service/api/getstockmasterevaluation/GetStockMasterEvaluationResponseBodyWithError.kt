package com.cmoney.backend2.ocean.service.api.getstockmasterevaluation

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetStockMasterEvaluationResponseBodyWithError(
    @SerializedName("AvgScores")
    val avgScores: Double?,
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ClosePr")
    val closePr: Double?,
    @SerializedName("Trends")
    val trends: List<Trend?>?
): CMoneyError(), IWithError<GetStockMasterEvaluationResponseBody> {
    override fun toRealResponse(): GetStockMasterEvaluationResponseBody {
        return GetStockMasterEvaluationResponseBody(
            avgScores,
            channelId,
            closePr,
            trends
        )
    }
}