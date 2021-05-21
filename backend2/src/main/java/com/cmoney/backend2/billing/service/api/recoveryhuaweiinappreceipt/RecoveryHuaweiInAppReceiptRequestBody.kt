package com.cmoney.backend2.billing.service.api.recoveryhuaweiinappreceipt

import com.cmoney.backend2.billing.service.common.InAppHuaweiReceipt
import com.google.gson.annotations.SerializedName

/**
 * 檢查是否有漏掉的華為授權商品的要求Body
 *
 * @property appId App編號
 * @property guid 會員guid
 * @property receipts 收據列表
 */
data class RecoveryHuaweiInAppReceiptRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Receipts")
    val receipts: List<InAppHuaweiReceipt>
)