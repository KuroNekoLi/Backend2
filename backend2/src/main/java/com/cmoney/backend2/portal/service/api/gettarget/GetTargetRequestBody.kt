package com.cmoney.backend2.portal.service.api.gettarget

import com.google.gson.annotations.SerializedName

data class GetTargetRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)