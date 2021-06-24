package com.cmoney.backend2.forumocean.service.api.comment.react

import com.google.gson.annotations.SerializedName

/**
 * 對回文做反應的request body
 *
 * @property reactionType 想要做的反應
 * @property originalReactionType 原本做的反應  如果沒有填null
 */
data class ReactCommentRequestBody(
    @SerializedName("reactionType")
    val reactionType : Int,
    @SerializedName("originalReactionType")
    val originalReactionType : Int?
)