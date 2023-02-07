package com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.successdeal

import com.google.gson.annotations.SerializedName

/**
 * 成交單
 *
 * @property accountId 帳號編號
 * @property actualCost 實際成本
 * @property debitAndCreditCost 借貸成本
 * @property commodityAveragePrice 商品均價(股票均價)
 * @property buySellType 買賣類型
 * @property commodityId 商品編號
 * @property successDealPrice 成交價
 * @property successDealVolume 成交數量
 * @property tradingNo 交易序號
 * @property handingFee 手續費
 * @property isEnteredAccount 是否入帳
 * @property isSuccess 是否成功入帳
 * @property memo 備註
 * @property delegateId 委託單編號
 * @property remainVolume 剩餘股量(剩餘數量)
 * @property shortSaleFee 借券費
 * @property successDealSerialNumber 成交序號(成交紀錄Sn)
 * @property marketUnit 市場交易單位，整股：1
 * @property transactionTax 交易稅
 * @property successDealTime 成交時間
 * @property transactionType 交易類型，現股: 1 / 融資: 2 / 融券: 3
 *
 */
data class SuccessDealOrder(
    @SerializedName("account")
    val accountId: Long?,
    @SerializedName("actualCost")
    val actualCost: String?,
    @SerializedName("borrow")
    val debitAndCreditCost: String?,
    @SerializedName("bsAvgPr")
    val commodityAveragePrice: String?,
    @SerializedName("buySellType")
    val buySellType: Int?,
    @SerializedName("commKey")
    val commodityId: String?,
    @SerializedName("dealPr")
    val successDealPrice: String?,
    @SerializedName("dealQty")
    val successDealVolume: String?,
    @SerializedName("dealTno")
    val tradingNo: Int?,
    @SerializedName("fee")
    val handingFee: String?,
    @SerializedName("flag")
    val isEnteredAccount: Boolean?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("memo")
    val memo: String?,
    @SerializedName("ordNo")
    val delegateId: Long?,
    @SerializedName("remainQty")
    val remainVolume: String?,
    @SerializedName("shortSellingFee")
    val shortSaleFee: String?,
    @SerializedName("sn")
    val successDealSerialNumber: Long?,
    @SerializedName("stockMarketType")
    val marketUnit: Int?,
    @SerializedName("tax")
    val transactionTax: String?,
    @SerializedName("te")
    val successDealTime: Long?,
    @SerializedName("tradeType")
    val transactionType: Int?
)