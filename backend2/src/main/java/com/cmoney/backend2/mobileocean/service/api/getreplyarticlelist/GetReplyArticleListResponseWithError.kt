package com.cmoney.backend2.mobileocean.service.api.getreplyarticlelist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetReplyArticleListResponseWithError(
    @SerializedName("Articles")
    val articles: List<ReplyArticle?>?
) : CMoneyError(),
    IWithError<GetReplyArticleListResponse> {

    override fun toRealResponse(): GetReplyArticleListResponse {
        return GetReplyArticleListResponse(articles)
    }
}