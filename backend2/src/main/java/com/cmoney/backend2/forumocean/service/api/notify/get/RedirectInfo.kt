package com.cmoney.backend2.forumocean.service.api.notify.get

import com.google.gson.annotations.SerializedName

/**
 * 轉導資訊
 *
 */
sealed class RedirectInfo{
    data class RedirectComment(
        @SerializedName("ArticleId")
        val articleId : Long?,
        @SerializedName("CommentIndex")
        val commentIndex : Int?
    ) : RedirectInfo()
    data class RedirectArticle(
        @SerializedName("ArticleId")
        val articleId: Long?
    ) : RedirectInfo()
    data class RedirectMember(
        @SerializedName("MemberId")
        val memberId : Long?
    ) : RedirectInfo()
    data class RedirectGroupArticle(
        @SerializedName("GroupId")
        val groupId : Long?,
        @SerializedName("ArticleId")
        val articleId: Long?
    ) : RedirectInfo()

    data class RedirectGroup(
        @SerializedName("GroupId")
        val groupId : Long?
    ) : RedirectInfo()
}
