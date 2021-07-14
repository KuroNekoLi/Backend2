package com.cmoney.backend2.ocean.service.api.getanswers

import com.cmoney.backend2.ocean.service.api.variable.MemberAnswer
import com.google.gson.annotations.SerializedName

data class GetAnswersResponse(
    @SerializedName("MemberAnswers")
    val memberAnswers: List<MemberAnswer?>?
)