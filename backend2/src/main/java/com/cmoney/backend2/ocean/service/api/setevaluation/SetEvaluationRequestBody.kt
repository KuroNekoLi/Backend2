package com.cmoney.backend2.ocean.service.api.setevaluation


import com.google.gson.annotations.SerializedName

data class SetEvaluationRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ChannelId")
    val channelId: Long,
    @SerializedName("Content")
    val content: String,
    @SerializedName("Score")
    val score: Int
)