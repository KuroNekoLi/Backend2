package com.cmoney.backend2.mobileocean.service.api.followchannel

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class FollowChannelResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<FollowChannelResponse> {

    override fun toRealResponse(): FollowChannelResponse {
        return FollowChannelResponse(isSuccess)
    }
}