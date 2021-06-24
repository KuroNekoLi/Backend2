package com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles

import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

class GetFollowedChannelArticlesResponse(
    /**
     * 文章清單
     */
    @SerializedName("Articles")
    val articles: List<Article?>?
)