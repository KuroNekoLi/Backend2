package com.cmoney.backend2.ocean.service.api.getchannelquestions

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.variable.Questionnaire
import com.google.gson.annotations.SerializedName

data class GetChannelQuestionsResponseWithError(
    @SerializedName("IsExist")
    val isExist: Boolean?,
    @SerializedName("Questionnaire")
    val questionnaire: Questionnaire?
) : CMoneyError(),
    IWithError<GetChannelQuestionsResponse> {
    override fun toRealResponse() =
        GetChannelQuestionsResponse(
            isExist,
            questionnaire
        )
}