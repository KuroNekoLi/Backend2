package com.cmoney.backend2.billing.service.api.recoverygooglesubreceipt

import com.cmoney.backend2.billing.service.common.SubGoogleReceipt
import com.google.gson.annotations.SerializedName

/**
 * 安卓恢復購買的Request參數
 *
 * @property appId App編號
 * @property guid 會員guid
 * @property receipts 購買收據
 *
 */
data class RecoveryGoogleSubReceiptRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Receipts")
    val receipts: List<SubGoogleReceipt>
)