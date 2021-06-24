package com.cmoney.backend2.ocean.service.api.getchannellatestarticle

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetChannelLatestArticleResponse(
    @SerializedName("Articles")
    val articles : List<Article?>?
)