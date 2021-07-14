package com.cmoney.backend2.notification2.service.api.getmroptionlist

import com.google.gson.annotations.SerializedName

data class GetMrOptionListResponseBody(
    @SerializedName("strategyId")
    val strategyId: Int?,
    @SerializedName("strategyName")
    val strategyName: String?,
    @SerializedName("allValues")
    val allValues: List<Int?>?,
    @SerializedName("defaultValues")
    val defaultValues: List<Int?>?,
    @SerializedName("monitorValues")
    val monitorValues: List<Int?>?
)