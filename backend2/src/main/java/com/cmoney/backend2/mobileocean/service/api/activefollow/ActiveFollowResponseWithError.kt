package com.cmoney.backend2.mobileocean.service.api.activefollow

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class ActiveFollowResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("Message")
    val message: String?
): CMoneyError(),
    IWithError<ActiveFollowResponse>{
    override fun toRealResponse(): ActiveFollowResponse {
        return ActiveFollowResponse(isSuccess, message)
    }
}