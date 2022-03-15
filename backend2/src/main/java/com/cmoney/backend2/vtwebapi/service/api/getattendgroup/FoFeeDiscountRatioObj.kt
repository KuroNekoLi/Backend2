package com.cmoney.backend2.vtwebapi.service.api.getattendgroup


import com.google.gson.annotations.SerializedName

/**
 * 期權手續費折扣率
 *
 * @property commKey 期權商品
 * @property commName 期權商品名稱
 * @property fee 手續費
 * @property feeDiscountRatio 手續費折扣率
 */
data class FoFeeDiscountRatioObj(
    @SerializedName("commKey")
    val commKey: String?,
    @SerializedName("commName")
    val commName: String?,
    @SerializedName("fee")
    val fee: Double?,
    @SerializedName("feeDiscountRatio")
    val feeDiscountRatio: Double?
)