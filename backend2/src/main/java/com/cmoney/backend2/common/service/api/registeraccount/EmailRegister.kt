package com.cmoney.backend2.common.service.api.registeraccount

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

/**
 * @param responseCode 1-成功 4、6、11- 帳號已註冊  10-該帳號已加入黑名單
 */
data class EmailRegister(
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