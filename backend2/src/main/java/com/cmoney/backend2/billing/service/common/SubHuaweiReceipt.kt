package com.cmoney.backend2.billing.service.common

import com.google.gson.annotations.SerializedName

/**
 * 華為訂閱訂單資料
 *
 * @property accountFlag 1: App touch帳號 其他:華為帳號
 * @property purchaseToken 購買產生的Token
 * @property productId 商品編號
 * @property subscriptionId 訂閱編號
 * @property receiptJson 訂單的Json字串
 * @property signature 訂單的簽章
 */
class SubHuaweiReceipt(
    @SerializedName("AccountFlag")
    val accountFlag: Int,
    @SerializedName("PurchaseToken")
    val purchaseToken: String,
    @SerializedName("ProductId")
    val productId: String,
    @SerializedName("SubscriptionId")
    val subscriptionId: String,
    @SerializedName("ReceiptJson")
    val receiptJson: String,
    @SerializedName("Sign")
    val signature: String
)