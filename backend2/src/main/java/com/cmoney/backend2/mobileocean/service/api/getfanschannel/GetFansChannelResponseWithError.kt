package com.cmoney.backend2.mobileocean.service.api.getfanschannel

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetFansChannelResponseWithError(
    @SerializedName("Channels")
    val channels: List<FansChannel>?
) : CMoneyError(), IWithError<GetFansChannelResponse> {
    override fun toRealResponse(): GetFansChannelResponse {
        return GetFansChannelResponse(channels)
    }
}
