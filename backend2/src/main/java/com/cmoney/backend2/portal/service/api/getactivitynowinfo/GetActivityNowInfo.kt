package com.cmoney.backend2.portal.service.api.getactivitynowinfo
import com.google.gson.annotations.SerializedName


data class GetActivityNowInfo(
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
)