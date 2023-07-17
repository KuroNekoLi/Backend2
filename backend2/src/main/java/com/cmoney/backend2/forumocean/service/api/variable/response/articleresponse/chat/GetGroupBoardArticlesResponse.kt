package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.google.gson.annotations.SerializedName

data class GetGroupBoardArticlesResponse(
    @SerializedName("articles")
    val articles: List<GroupArticlesResponseBody>?,
    @SerializedName("hasNext")
    val hasNext: Boolean?,
    @SerializedName("nextStartWeight")
    val nextStartWeight: Long?
)
