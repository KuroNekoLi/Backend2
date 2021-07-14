package com.cmoney.backend2.mobileocean.service.api.getforumpopulararticles


import com.google.gson.annotations.SerializedName

data class GetForumPopularArticlesResponse(
    @SerializedName("Articles")
    val articles: List<PopularArticle?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
)