package com.cmoney.backend2.chat.service.api.reportsomeone.request

import com.google.gson.annotations.SerializedName

data class ReportSomeone(
    @SerializedName("description")
    val description: String,
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("userId")
    val userId: Long
)