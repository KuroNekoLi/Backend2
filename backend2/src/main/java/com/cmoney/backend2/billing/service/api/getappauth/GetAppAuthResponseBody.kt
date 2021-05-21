package com.cmoney.backend2.billing.service.api.getappauth

import com.cmoney.backend2.base.model.response.error.ISuccess

import com.google.gson.annotations.SerializedName

/**
 * @param authType 該會員的權限狀態(0-免費,1-購買理財寶,2-購買手機授權)
 * @param authExpTime 權限到期日(yyyy/MM/dd)
 *
 */
data class GetAppAuthResponseBody(
    @SerializedName("AuthType", alternate = ["authType"])
    val authType: Int?,
    @SerializedName("AuthExpTime", alternate = ["authExpTime"])
    val authExpTime: String?,
    @SerializedName("ResponseCode", alternate = ["responseCode"])
    val responseCode: Int?,
    @SerializedName("ResponseMsg", alternate = ["responseMsg"])
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