package com.cmoney.backend2.ocean.service.api.getblocklist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetBlockListResponseBodyWithError(
    @SerializedName("ChannelIds")
    val channelIds: List<Long?>?
) : CMoneyError(),
    IWithError<GetBlockListResponseBody> {
    override fun toRealResponse(): GetBlockListResponseBody {
        return GetBlockListResponseBody(channelIds)
    }
}