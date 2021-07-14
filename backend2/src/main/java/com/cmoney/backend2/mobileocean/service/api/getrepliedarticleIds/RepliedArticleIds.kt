package com.cmoney.backend2.mobileocean.service.api.getrepliedarticleIds

import com.google.gson.annotations.SerializedName

data class RepliedArticleIds(
    @SerializedName("RepliedArticle")
    val repliedArticle: List<Long?>?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
)