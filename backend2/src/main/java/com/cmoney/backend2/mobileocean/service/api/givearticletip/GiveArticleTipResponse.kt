package com.cmoney.backend2.mobileocean.service.api.givearticletip

import com.google.gson.annotations.SerializedName

data class GiveArticleTipResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)