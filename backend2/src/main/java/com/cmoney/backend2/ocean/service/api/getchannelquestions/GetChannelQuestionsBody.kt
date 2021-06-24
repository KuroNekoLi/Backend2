package com.cmoney.backend2.ocean.service.api.getchannelquestions

import com.google.gson.annotations.SerializedName

data class GetChannelQuestionsBody(
    @SerializedName("ChannelId")
    val channelId : Long,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)