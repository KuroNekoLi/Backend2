package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articlestate

import com.google.gson.annotations.SerializedName

enum class ArticleState {
    @SerializedName("blocked", alternate = ["Blocked"])
    Block,
    @SerializedName("deleted", alternate = ["Deleted"])
    Delete,
    @SerializedName("unreadableNote", alternate = ["UnreadableNote"])
    UnreadableNote
}