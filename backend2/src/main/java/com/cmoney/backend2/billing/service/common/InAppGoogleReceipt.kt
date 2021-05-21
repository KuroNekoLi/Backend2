package com.cmoney.backend2.billing.service.common

import com.google.gson.annotations.SerializedName

/**
 * Google一般商品訂單資料
 *
 * @param purchaseToken 購買產生的Token
 * @param productId 商品編號
 */
data class InAppGoogleReceipt(
    @SerializedName("PurchaseToken")
    val purchaseToken: String,
    @SerializedName("ProductId")
    val productId: String
)