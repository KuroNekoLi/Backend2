package com.cmoney.backend2.ocean.service.api.getrelevantcomments

import com.google.gson.annotations.SerializedName

data class GetRelevantCommentsRequest(
    @SerializedName("ArticleIds")
    val articleIds: List<Long>,
    @SerializedName("Fetch")
    val fetch: Long,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)
