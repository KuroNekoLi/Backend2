package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articlestate

import com.google.gson.annotations.SerializedName

enum class ArticleState {
    @SerializedName("blocked")
    Block,
    @SerializedName("deleted")
    Delete,
    @SerializedName("unreadableNote")
    UnreadableNote
}