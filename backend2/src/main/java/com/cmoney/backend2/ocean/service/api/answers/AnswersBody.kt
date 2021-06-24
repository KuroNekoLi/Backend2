package com.cmoney.backend2.ocean.service.api.answers

import com.cmoney.backend2.ocean.service.api.variable.AnswerParam
import com.google.gson.annotations.SerializedName

data class AnswersBody(
    @SerializedName("Answers")
    val answers: List<AnswerParam>,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)