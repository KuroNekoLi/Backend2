package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class ArticleResult(
    @SerializedName("Article")
    val article : Article?,

    @SerializedName("Comments")
    val commentResult: List<Comment>?
)