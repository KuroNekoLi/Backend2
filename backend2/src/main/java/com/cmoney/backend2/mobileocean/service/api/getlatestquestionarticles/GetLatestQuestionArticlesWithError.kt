package com.cmoney.backend2.mobileocean.service.api.getlatestquestionarticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

class GetLatestQuestionArticlesResponseWithError(
    @SerializedName("Articles")
    val articles: List<Article?>?
) : CMoneyError() , IWithError<GetLatestQuestionArticlesResponse> {
    override fun toRealResponse(): GetLatestQuestionArticlesResponse {
        return GetLatestQuestionArticlesResponse(articles)
    }
}