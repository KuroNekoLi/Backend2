package com.cmoney.backend2.ocean.service.api.deletearticle

import com.google.gson.annotations.SerializedName

data class DeleteArticleRequestBody(
    @SerializedName("AppId")
    val appId: Int?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("Guid")
    val guid: String?
)
