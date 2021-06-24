package com.cmoney.backend2.ocean.service.api.isinwhitelist


import com.google.gson.annotations.SerializedName

data class IsInCreateArticleWhiteListResponseBody(
    @SerializedName("IsInWhiteList")
    val isInWhiteList: Boolean?
)