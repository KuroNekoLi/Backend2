package com.cmoney.backend2.mobileocean.service.api.dislikearticle

import com.google.gson.annotations.SerializedName

data class DisLikeArticleResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)