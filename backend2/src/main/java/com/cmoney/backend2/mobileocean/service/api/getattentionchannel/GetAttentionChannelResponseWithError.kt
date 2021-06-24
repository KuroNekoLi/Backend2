package com.cmoney.backend2.mobileocean.service.api.getattentionchannel


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetAttentionChannelResponseWithError(
    @SerializedName("Channels")
    val channels: List<AttentionChannel>?
) : CMoneyError(), IWithError<GetAttentionChannelResponse> {
    override fun toRealResponse(): GetAttentionChannelResponse {
        return GetAttentionChannelResponse(channels)
    }
}