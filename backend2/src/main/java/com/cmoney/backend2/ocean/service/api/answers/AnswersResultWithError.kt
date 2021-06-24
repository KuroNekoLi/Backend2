package com.cmoney.backend2.ocean.service.api.answers

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.getanswers.GetAnswersResponse
import com.cmoney.backend2.ocean.service.api.variable.MemberAnswer
import com.google.gson.annotations.SerializedName

data class AnswersResultWithError(
    @SerializedName("MemberAnswers")
    val memberAnswers: List<MemberAnswer?>?
) : CMoneyError(), IWithError<GetAnswersResponse> {
    override fun toRealResponse() =
        GetAnswersResponse(memberAnswers)
}