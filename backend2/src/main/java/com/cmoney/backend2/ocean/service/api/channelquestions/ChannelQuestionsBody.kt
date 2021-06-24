package com.cmoney.backend2.ocean.service.api.channelquestions

import com.google.gson.annotations.SerializedName

data class ChannelQuestionsBody(
    @SerializedName("Questionnaire")
    val questionnaire : ChannelQuestionnaire,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)