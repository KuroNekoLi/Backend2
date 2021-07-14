package com.cmoney.backend2.ocean.service.api.getchannelquestions

import com.cmoney.backend2.ocean.service.api.variable.Questionnaire
import com.google.gson.annotations.SerializedName

data class GetChannelQuestionsResponse(
    @SerializedName("IsExist")
    val isExist: Boolean?,
    @SerializedName("Questionnaire")
    val questionnaire: Questionnaire?
)