package com.cmoney.backend2.portal.service.api.getmemberperformance

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetMemberPerformanceWithError(
    @SerializedName("RatioOfWin")
    val ratioOfWin: Double?,
    @SerializedName("TotalWinTimes")
    val totalWinTimes: Int?
) : CMoneyError(),IWithError<GetMemberPerformance> {
    override fun toRealResponse(): GetMemberPerformance {
        return GetMemberPerformance(
            ratioOfWin,
            totalWinTimes
        )
    }
}