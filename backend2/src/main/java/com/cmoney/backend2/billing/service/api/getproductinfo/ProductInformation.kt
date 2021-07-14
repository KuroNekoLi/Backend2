package com.cmoney.backend2.billing.service.api.getproductinfo

import com.google.gson.annotations.SerializedName

/**
 * 商品資訊
 *
 * @property appId 所屬應用程式編號
 * @property device 裝置類型
 * @property itemId IOS Item Id
 * @property mobileProductSn 產品流水號
 * @property packageName Android:Package Name IOS:bundle id
 * @property price 價格
 * @property productId 商品編號(於Google or AppId後台唯一編號)
 * @property productName 商品名稱
 * @property productType CMoney商品類型
 * @property purchaseAuthType 購買授權類型，0-未指定;1-一個月;2-半年;3-一年;4-360天授權;5-一季(三個月)
 * @property purchaseType 購買類型，0-未指定;1-消耗型商品;2-非消耗型商品;3-自動續約型商品;4-不會自動需約型商品
 */
data class ProductInformation(
    @SerializedName("appId")
    val appId: Int?,
    @SerializedName("device")
    val device: Int?,
    @SerializedName("itemId")
    val itemId: Long?,
    @SerializedName("mobileProductSn")
    val mobileProductSn: Int?,
    @SerializedName("packageName")
    val packageName: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("productId")
    val productId: String?,
    @SerializedName("productName")
    val productName: String?,
    @SerializedName("productType")
    val productType: Int?,
    @SerializedName("purchaseAuthType")
    val purchaseAuthType: Int?,
    @SerializedName("purchaseType")
    val purchaseType: Int?
)