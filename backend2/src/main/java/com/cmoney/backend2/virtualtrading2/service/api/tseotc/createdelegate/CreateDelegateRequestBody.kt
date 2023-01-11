package com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate


import com.google.gson.annotations.SerializedName

/**
 * 建立委託單
 *
 * @property accountId 帳號編號
 * @property buySellType 買賣類型，買單: 66 / 賣單: 83
 * @property commodityId 商品代號
 * @property subsistingType 存續種類
 * 立即成交否則取消單(IOC) 73 : 所下委託單必須馬上成交，否則即行取消之委託方式。
 * 立即全部成交否則作廢單(FOK) 70 : 所下委託單必須立即且全部成交，否則即行取消之委託方式。
 * 當日有效單(ROD) 82 : 所下委託單限當日有效。
 * @property groupId 競技場編號
 * @property delegatePrice 委託價
 * @property delegateVolume 委託量(股)
 * @property marketUnit 市場交易單位，整股: 1
 * @property transactionType 交易類型，現股: 1 / 融資: 2 / 融券: 3
 *
 */
data class CreateDelegateRequestBody(
    @SerializedName("accountId")
    val accountId: Long,
    @SerializedName("buySellType")
    val buySellType: Int,
    @SerializedName("commKey")
    val commodityId: String,
    @SerializedName("condition")
    val subsistingType: Int,
    @SerializedName("groupId")
    val groupId: Long,
    @SerializedName("ordPr")
    val delegatePrice: Double,
    @SerializedName("ordQty")
    val delegateVolume: Long,
    @SerializedName("stkTradeType")
    val marketUnit: Int,
    @SerializedName("tradeType")
    val transactionType: Int
)