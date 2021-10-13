package com.cmoney.backend2.forumocean.service.api.notify.get

import com.google.gson.annotations.SerializedName

/**
 * 轉導資訊
 *
 */
sealed class RedirectInfo{
    data class RedirectComment(
        @SerializedName("articleId")
        val articleId : Long?,
        @SerializedName("commentIndex")
        val commentIndex : Int?
    ) : RedirectInfo()
    data class RedirectArticle(
        @SerializedName("articleId")
        val articleId: Long?
    ) : RedirectInfo()
    data class RedirectMember(
        @SerializedName("memberId")
        val memberId : Long?
    ) : RedirectInfo()
    data class RedirectGroupArticle(
        @SerializedName("groupId")
        val groupId : Long?,
        @SerializedName("articleId")
        val articleId: Long?
    ) : RedirectInfo()

    data class RedirectGroup(
        @SerializedName("groupId")
        val groupId : Long?
    ) : RedirectInfo()
}
