package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

/**
 * 請加入以下欄位
 * @SerializedName("sharedPostsArticleId")
 * val sharedPostsArticleId: Long
 *
 */
interface ShareArticleInfo {
    /**
     * 轉推文章Id
     */
    val sharedPostsArticleId: Long?
}