package com.cmoney.backend2.crm.service.api.creatlivechat

import com.google.gson.annotations.SerializedName

data class CreateLiveChatResponseBody(
    @SerializedName("liveChatUrl")
    val liveChatUrl: String?
)