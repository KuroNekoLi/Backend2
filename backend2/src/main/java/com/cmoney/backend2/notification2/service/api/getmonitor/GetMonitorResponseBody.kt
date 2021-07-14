package com.cmoney.backend2.notification2.service.api.getmonitor

import com.google.gson.annotations.SerializedName

/**
 * 到價監控的所有設定
 */
data class GetMonitorResponseBody(
    @SerializedName("conditionId", alternate = ["ConditionId"])
    val conditionId: Long?,
    @SerializedName("commKey", alternate = ["CommKey"])
    val commonKey: String?,
    @SerializedName("strategyId", alternate = ["StrategyId"])
    val strategyId: Int?,
    @SerializedName("isNeedPush", alternate = ["IsNeedPush"])
    val isNeedPush: Boolean?,
    @SerializedName("condition", alternate = ["Condition"])
    val condition: Condition?
) {
    data class Condition(
        @SerializedName("TargetPrice", alternate = ["targetPrice"])
        val targetPrice: Double?
    )
}
