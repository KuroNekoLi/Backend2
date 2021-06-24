package com.cmoney.backend2.mobileocean.service.api.getrepliedarticleIds

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.ISuccess
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class RepliedArticleIdsWithError(
    @SerializedName("RepliedArticle")
    val repliedArticle: List<Long?>?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
) : CMoneyError(), IWithError<RepliedArticleIds>, ISuccess {

    override fun toRealResponse(): RepliedArticleIds {
        return RepliedArticleIds(repliedArticle, responseCode, responseMsg)
    }

    override fun getErrorCode(): Int {
        return detail?.code ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun getErrorMessage(): String {
        return detail?.message ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 1
    }
}