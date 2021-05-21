package com.cmoney.backend2.billing.service.api.verifygoogleinappreceipt

import com.cmoney.backend2.billing.service.common.InAppGoogleReceipt
import com.google.gson.annotations.SerializedName

/**
 * Google驗證訂單的要求Body
 *
 * @property appId App編號
 * @property guid 會員guid
 * @property receipt 收據
 */
data class VerifyGoogleInAppReceiptRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Receipt")
    val receipt: InAppGoogleReceipt
)