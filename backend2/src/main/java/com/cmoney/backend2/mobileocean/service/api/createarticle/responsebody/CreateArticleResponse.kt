package com.cmoney.backend2.mobileocean.service.api.createarticle.responsebody

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

data class CreateArticleResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,

    @SerializedName("ResponseCode")
    val responseCode: Int?,

    @SerializedName("NewArticleId")
    val newArticleId: Long?
) : ISuccess {
    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun getErrorMessage(): String {
        return when (this.responseCode) {
            267998 -> "因您的發文被多次檢舉，系統自動禁言24小時"
            204 -> "發佈失敗:請稍後再試！"
            701026 -> "您送出的訊息內含有不當言詞，請修改後再嘗試!"
            701027 -> "發佈失敗:30秒內重複發文"
            206 -> "發佈失敗:解析圖片錯誤！"
            205 -> "發佈失敗:圖片中包含敏感字眼！"
            202 -> "發佈失敗:圖片檔名錯誤！"
            203 -> "發佈失敗:圖片格式錯誤！"
            201 -> "發佈失敗:圖片太長或太寬！"
            else -> {
                ""
            }
        }
    }

    override fun isResponseSuccess(): Boolean {
        return isSuccess ?: true
    }
}