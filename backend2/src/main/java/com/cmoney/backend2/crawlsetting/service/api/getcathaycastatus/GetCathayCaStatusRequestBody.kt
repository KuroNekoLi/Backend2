package com.cmoney.backend2.crawlsetting.service.api.getcathaycastatus

import com.google.gson.annotations.SerializedName

data class GetCathayCaStatusRequestBody(
    @SerializedName("userInfoKey")
    val userInfoKey: String
)
