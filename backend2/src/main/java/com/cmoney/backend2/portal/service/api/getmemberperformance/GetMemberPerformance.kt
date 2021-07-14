package com.cmoney.backend2.portal.service.api.getmemberperformance

import com.google.gson.annotations.SerializedName

data class GetMemberPerformance(
    @SerializedName("RatioOfWin")
    val ratioOfWin: Double?,
    @SerializedName("TotalWinTimes")
    val totalWinTimes: Int?
)