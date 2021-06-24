package com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo

import com.google.gson.annotations.SerializedName

data class AddInterestedInArticleInfoResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)