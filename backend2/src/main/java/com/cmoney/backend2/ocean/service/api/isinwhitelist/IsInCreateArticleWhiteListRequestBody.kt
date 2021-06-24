package com.cmoney.backend2.ocean.service.api.isinwhitelist


import com.google.gson.annotations.SerializedName

data class IsInCreateArticleWhiteListRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)