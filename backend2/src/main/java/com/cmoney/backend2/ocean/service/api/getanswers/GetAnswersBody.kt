package com.cmoney.backend2.ocean.service.api.getanswers


import com.google.gson.annotations.SerializedName

data class GetAnswersBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ChannelId")
    val channelId: Long,
    @SerializedName("MemberChannelIds")
    val memberChannelIds: List<Long>,
    @SerializedName("QuestionIds")
    val questionIds: List<Long>
)