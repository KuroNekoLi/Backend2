package com.cmoney.backend2.mobileocean.service.api.updatechanneldescription

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UpdateChannelIdDescriptionResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<UpdateChannelIdDescriptionResponse> {

    override fun toRealResponse(): UpdateChannelIdDescriptionResponse {
        return UpdateChannelIdDescriptionResponse(isSuccess)
    }
}