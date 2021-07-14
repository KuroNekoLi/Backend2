package com.cmoney.backend2.ocean.service.api.getmanagerlist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.google.gson.annotations.SerializedName

data class GetManagerListResponseWithError(
    @SerializedName("Creator")
    val creator: ChannelInfo.MemberChannelInfo?,
    @SerializedName("Managers")
    val managers: List<ChannelInfo.MemberChannelInfo?>?
):CMoneyError(), IWithError<GetManagerList> {
    override fun toRealResponse(): GetManagerList {
        return GetManagerList(creator, managers)
    }
}