package com.cmoney.backend2.common.service.api.getconfig

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

data class GetConfigResponseBody(
    @SerializedName("DpscPort")
    val dpscPort: Int?,
    @SerializedName("DpscChkSum")
    val dpscChkSum: Int?,
    @SerializedName("ServerUrl")
    val serverUrl: String?,
    @SerializedName("ServerPushUrl")
    val serverPushUrl: String?,
    @SerializedName("VipServerUrl")
    val vipServerUrl: String?,
    @SerializedName("VipServerPushUrl")
    val vipServerPushUrl: String?,
    @SerializedName("StatusAnnouncement")
    val statusAnnouncement: String?,
    @SerializedName("AppStatus")
    val appStatus: AppStatus?,
    @SerializedName("IsUnderReview")
    val isUnderReview: Boolean?,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
): ISuccess {
    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun getErrorMessage(): String {
        return responseMsg ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 1
    }
}