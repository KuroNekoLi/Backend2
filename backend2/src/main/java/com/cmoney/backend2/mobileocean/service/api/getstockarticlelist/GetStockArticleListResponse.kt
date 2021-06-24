package com.cmoney.backend2.mobileocean.service.api.getstockarticlelist

import com.google.gson.annotations.SerializedName

data class GetStockArticleListResponse(
    @SerializedName("Articles")
    val articles: List<Article?>?
)