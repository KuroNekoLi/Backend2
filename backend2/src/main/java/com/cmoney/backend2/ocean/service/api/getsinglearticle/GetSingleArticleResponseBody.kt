package com.cmoney.backend2.ocean.service.api.getsinglearticle

import com.cmoney.backend2.ocean.service.api.variable.ArticleResult
import com.google.gson.annotations.SerializedName

data class GetSingleArticleResponseBody(
    @SerializedName("Data")
    val data : ArticleResult?
)