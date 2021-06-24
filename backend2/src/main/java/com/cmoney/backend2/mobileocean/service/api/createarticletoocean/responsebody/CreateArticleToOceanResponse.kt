package com.cmoney.backend2.mobileocean.service.api.createarticletoocean.responsebody

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

data class CreateArticleToOceanResponse (
    @SerializedName("ArticleId")
    val articleId : Long?,

    @SerializedName("ResponseCode")
    val responseCode : Int?,

    @SerializedName("ResponseMsg")
    val responseMsg : String?,

    @SerializedName("Error")
    val errorMsg: String? = ""
) : ISuccess {
    /**
     * ResponseCode  1-成功, 2-沒有發文內容, 3-系統版本、名稱為空
     */
    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun getErrorMessage(): String {
        return responseMsg ?: errorMsg?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 1
    }
}