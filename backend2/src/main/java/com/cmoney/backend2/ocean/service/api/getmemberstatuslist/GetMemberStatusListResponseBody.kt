package com.cmoney.backend2.ocean.service.api.getmemberstatuslist

import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.google.gson.annotations.SerializedName

data class GetMemberStatusListResponseBody(
    @SerializedName("Channels")
    val channels: List<ChannelInfo.MemberChannelInfo?>?
)