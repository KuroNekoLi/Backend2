package com.cmoney.backend2.billing.service.api.verifygooglesubreceipt

import com.cmoney.backend2.billing.service.common.SubGoogleReceipt
import com.google.gson.annotations.SerializedName

/**
 * Google驗證訂單的要求Body
 *
 * @property appId App編號
 * @property guid 會員guid
 * @property receipt 收據
 */
data class VerifyGoogleSubReceiptRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Receipt")
    val receipt: SubGoogleReceipt
)