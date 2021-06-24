package com.cmoney.backend2.ocean.service.api.getmanagerlist

import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.google.gson.annotations.SerializedName

data class GetManagerList(
    @SerializedName("Creator")
    val creator: ChannelInfo.MemberChannelInfo?,
    @SerializedName("Managers")
    val managers: List<ChannelInfo.MemberChannelInfo?>?
)