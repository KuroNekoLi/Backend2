package com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetFollowedChannelArticlesResponseWithError(
    /**
     * 文章清單
     */
    @SerializedName("Articles")
    val articles: List<Article?>?
)  : CMoneyError(),
    IWithError<GetFollowedChannelArticlesResponse> {
    override fun toRealResponse(): GetFollowedChannelArticlesResponse {
        return GetFollowedChannelArticlesResponse(articles)
    }
}