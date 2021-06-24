package com.cmoney.backend2.ocean.service.api.getCollectArticleList

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

class GetCollectArticleListResponseBodyWithError (
    @SerializedName("Articles")
    val articles : List<Article?>?
): CMoneyError(), IWithError<GetCollectArticleListResponseBody> {
    override fun toRealResponse(): GetCollectArticleListResponseBody {
        return GetCollectArticleListResponseBody(articles)
    }
}