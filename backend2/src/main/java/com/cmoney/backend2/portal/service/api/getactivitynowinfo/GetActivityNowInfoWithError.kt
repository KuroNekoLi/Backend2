package com.cmoney.backend2.portal.service.api.getactivitynowinfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetActivityNowInfoWithError(
    @SerializedName("ActivityEndTime")
    val activityEndTime: Long?,
    @SerializedName("EndTradingTime")
    val endTradingTime: Long?,
    @SerializedName("RefPr")
    val refPr: Double?,
    @SerializedName("StartTradingTime")
    val startTradingTime: Long?,
    @SerializedName("TendToBearAmount")
    val tendToBearAmount: Int?,
    @SerializedName("TendToBullAmount")
    val tendToBullAmount: Int?,
    @SerializedName("TotalBets")
    val totalBets: Int?,
    @SerializedName("TotalParticipants")
    val totalParticipants: Int?
): CMoneyError(),IWithError<GetActivityNowInfo>{
    override fun toRealResponse(): GetActivityNowInfo {
        return GetActivityNowInfo(
            activityEndTime,
            endTradingTime,
            refPr,
            startTradingTime,
            tendToBearAmount,
            tendToBullAmount,
            totalBets,
            totalParticipants
        )
    }

}