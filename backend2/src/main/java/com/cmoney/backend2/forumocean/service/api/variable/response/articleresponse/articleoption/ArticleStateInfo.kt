package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articlestate.ArticleState

/**
 * 請加入以下欄位
 * @SerializedName("articleState")
 * val articleState : ArticleState?
 *
 */
interface ArticleStateInfo {
    /**
     * 文章狀態
     */
    val articleState : ArticleState?
}