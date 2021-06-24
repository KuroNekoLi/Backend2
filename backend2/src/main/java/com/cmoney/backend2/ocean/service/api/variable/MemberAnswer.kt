package com.cmoney.backend2.ocean.service.api.variable


import com.google.gson.annotations.SerializedName

data class MemberAnswer(
    @SerializedName("Answers")
    val answers: List<Answer?>?,
    @SerializedName("MemberChannelId")
    val memberChannelId: Long?
)