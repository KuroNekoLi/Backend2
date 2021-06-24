package com.cmoney.backend2.mobileocean.service.api.likearticle

import com.google.gson.annotations.SerializedName

data class LikeArticleResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)