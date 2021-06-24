package com.cmoney.backend2.ocean.service.api.getblacklist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetBlackListResponseBodyWithError(
    @SerializedName("ChannelIds")
    val channelIds: List<Long?>?
) : CMoneyError(),
    IWithError<GetBlackListResponseBody> {
    override fun toRealResponse(): GetBlackListResponseBody {
        return GetBlackListResponseBody(channelIds)
    }
}