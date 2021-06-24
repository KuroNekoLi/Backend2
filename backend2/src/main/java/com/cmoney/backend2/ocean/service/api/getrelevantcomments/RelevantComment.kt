package com.cmoney.backend2.ocean.service.api.getrelevantcomments

import com.google.gson.annotations.SerializedName

data class RelevantComment(
    @SerializedName("HeadArticleId")
    val headArticleId: Int,
    @SerializedName("Comments")
    val comments: List<Comment>
)
