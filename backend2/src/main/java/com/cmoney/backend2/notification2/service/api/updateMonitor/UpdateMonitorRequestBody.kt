package com.cmoney.backend2.notification2.service.api.updateMonitor

import com.google.gson.annotations.SerializedName

data class UpdateMonitorRequestBody(
    @SerializedName("ConditionId")
    val conditionId: Long,
    @SerializedName("StrategyId")
    val strategyId: Int,
    @SerializedName("Condition")
    val condition: Condition,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("AppId")
    val appId: Int
) {
    data class Condition(
        @SerializedName("TargetPrice", alternate = ["targetPrice"])
        val targetPrice: Double?
    )
}