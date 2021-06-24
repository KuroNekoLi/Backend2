package com.cmoney.backend2.mobileocean.service.api.replyarticle

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

/**
 * 回應文章
 *
 * @property isSuccess
 * @property responseCode 0-成功,201-超過上限,202-找不到圖片副檔名,203-圖片格式錯誤,204-例外,205-圖片中包含黑名單字眼,206-呼叫圖片解析服務異常(請找振維)
 * @property newArticleId
 */
data class ReplyArticleResponse(
    @SerializedName("IsSuccess")
    var isSuccess: Boolean? = null,
    @SerializedName("ResponseCode")
    var responseCode: Int? = null,
    @SerializedName("NewArticleId")
    var newArticleId: Long? = null
) : ISuccess {
    override fun getErrorMessage(): String {
        return responseCode?.toString() ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 0
    }

}