package com.cmoney.backend2.mobileocean.service.api.getforumlatestarticles


import com.google.gson.annotations.SerializedName

data class GetForumLatestArticlesResponse(
    @SerializedName("Articles")
    val articles: List<LatestArticle?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
)