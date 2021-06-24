package com.cmoney.backend2.mobileocean.service.api.getreplyarticlelist


import com.google.gson.annotations.SerializedName

data class GetReplyArticleListResponse(
    @SerializedName("Articles")
    val articles: List<ReplyArticle?>?
)