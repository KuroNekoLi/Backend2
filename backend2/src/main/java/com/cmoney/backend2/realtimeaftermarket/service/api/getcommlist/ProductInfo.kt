package com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist


import com.google.gson.annotations.SerializedName

/**
 * 商品明細
 *
 * @property commKey 編號
 * @property countryCode 國碼
 * @property isShowPreviousClosePr 是否需要授權
 * @property name 中文名稱
 * @constructor Create empty Product info
 */
data class ProductInfo(
    @SerializedName("Commkey")
    val commKey: String?,
    @SerializedName("CountryCode")
    val countryCode: Int?,
    @SerializedName("IsShowPreviousClosePr")
    val isShowPreviousClosePr: Boolean?,
    @SerializedName("Name")
    val name: String?
)