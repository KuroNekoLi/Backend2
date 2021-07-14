package com.cmoney.backend2.notification2.service.api.updatemroptionlist

import com.google.gson.annotations.SerializedName

data class UpdateMrOptionConditionRequestBody(
    @SerializedName("Conditions")
    val conditions: List<Condition>,
    @SerializedName("AppId")
    val appId: Int
) {

    data class Condition(
        @SerializedName("StrategyId")
        val strategyId: Int,
        @SerializedName("MonitorValues")
        val monitorValues: List<Int>
    )
}