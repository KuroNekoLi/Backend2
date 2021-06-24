package com.cmoney.backend2.common.service.api.forgotpasswordemail

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

/**
 * @param responseCode 1-正常,5-格式錯誤,6-帳號不存在,3-已申請,請等一分鐘
 */
data class EmailForgotPassword(
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
): ISuccess {

    override fun getErrorMessage(): String {
        return responseMsg ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 1
    }
}