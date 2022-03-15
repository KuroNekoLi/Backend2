package com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard

import com.google.gson.annotations.SerializedName

/**
 * @property instanceSn 購買之道具卡實際生成ID
 */
data class PurchaseProductCardResponseBody(
    @SerializedName("instanceSn")
    val instanceSn: Long
)