package com.cmoney.backend2.ocean.service.api.channelquestions

import com.cmoney.backend2.ocean.service.api.variable.QuestionParam
import com.google.gson.annotations.SerializedName

data class ChannelQuestionnaire(
    @SerializedName("ChannelId")
    val channelId : Long,
    @SerializedName("Questions")
    val questions: List<QuestionParam>
)