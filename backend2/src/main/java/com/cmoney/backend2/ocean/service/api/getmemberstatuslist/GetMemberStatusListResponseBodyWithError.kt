package com.cmoney.backend2.ocean.service.api.getmemberstatuslist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.google.gson.annotations.SerializedName

data class GetMemberStatusListResponseBodyWithError(
    @SerializedName("Channels")
    val channels: List<ChannelInfo.MemberChannelInfo?>?
): CMoneyError(), IWithError<GetMemberStatusListResponseBody> {
    override fun toRealResponse(): GetMemberStatusListResponseBody {
        return GetMemberStatusListResponseBody(channels)
    }
}