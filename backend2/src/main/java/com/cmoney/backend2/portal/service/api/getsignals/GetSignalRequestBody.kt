package com.cmoney.backend2.portal.service.api.getsignals

import com.google.gson.annotations.SerializedName

data class GetSignalRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)