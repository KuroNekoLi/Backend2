package com.cmoney.backend2.billing.service.api.verifyhuaweisubreceipt

import com.cmoney.backend2.billing.service.common.SubHuaweiReceipt
import com.google.gson.annotations.SerializedName

/**
 * 華為驗證訂單的要求Body
 *
 * @property appId App編號
 * @property guid 會員guid
 * @property receipt 收據
 */
data class VerifyHuaweiSubReceiptRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Receipt")
    val receipt: SubHuaweiReceipt
)