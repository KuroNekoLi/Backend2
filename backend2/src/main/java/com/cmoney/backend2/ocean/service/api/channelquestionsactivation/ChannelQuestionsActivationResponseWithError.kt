package com.cmoney.backend2.ocean.service.api.channelquestionsactivation

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class ChannelQuestionsActivationResponseWithError(
    @SerializedName("IsActive")
    val isActive: Boolean?
) : CMoneyError(),
    IWithError<ChannelQuestionsActivationResponse> {
    override fun toRealResponse() =
        ChannelQuestionsActivationResponse(isActive)
}