package com.cmoney.backend2.common.service.api.getaccesstoken


import com.google.gson.annotations.SerializedName

data class GetAccessToken(
    @SerializedName("AccessToken")
    val accessToken: String?
)