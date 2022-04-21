package com.cmoney.backend2.crawlsetting.service.api.gettaishincastatus

import com.google.gson.annotations.SerializedName

data class GetTaishinCaStatusRequestBody(
    @SerializedName("userInfoKey")
    val userInfoKey: String
)
