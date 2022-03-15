package com.cmoney.backend2.vtwebapi.service.api.getstockinventorylist


import com.google.gson.annotations.SerializedName

/**
 * @property account 帳號
 * @property boardLostSize 交易單位
 * @property canOrderQty 可下單量
 * @property commName 股票名稱
 * @property commkey 股票代號
 * @property cost 投入成本(買入市值)
 * @property dealAvgPr 平均成交價
 * @property groupId 競賽Id
 * @property incomeLoss 未實現損益
 * @property incomeLossWithoutPreFree 未實現損益(不含預扣稅率)
 * @property inventoryQty 庫存量
 * @property mainRatio 維持率(%)
 * @property marketValue 總市值(需另行計算)
 * @property nowPr 市場價格
 * @property ratio 報酬率(%)
 * @property shortSellingFee 預算借券手續費
 * @property showCost 顯示成本(現股買入市值、融資自備款、融券保證金)
 * @property taxcost 預算交易稅
 * @property todayInventoryQty 今日成交庫存
 * @property tradeKind 交易別 1：現股 2：融資 3：融券
 * @property tradeName 交易類別
 */
data class GetStockInventoryListResponseBody(
    @SerializedName("account")
    val account: Long?,
    @SerializedName("boardLostSize")
    val boardLostSize: Int?,
    @SerializedName("canOrderQty")
    val canOrderQty: Int?,
    @SerializedName("commName")
    val commName: String?,
    @SerializedName("commkey")
    val commkey: String?,
    @SerializedName("cost")
    val cost: Double?,
    @SerializedName("dealAvgPr")
    val dealAvgPr: Double?,
    @SerializedName("groupId")
    val groupId: Long?,
    @SerializedName("incomeLoss")
    val incomeLoss: Double?,
    @SerializedName("incomeLossWithoutPreFree")
    val incomeLossWithoutPreFree: Double?,
    @SerializedName("inventoryQty")
    val inventoryQty: Int?,
    @SerializedName("mainRatio")
    val mainRatio: Double?,
    @SerializedName("marketValue")
    val marketValue: Double?,
    @SerializedName("nowPr")
    val nowPr: Double?,
    @SerializedName("ratio")
    val ratio: Double?,
    @SerializedName("shortSellingFee")
    val shortSellingFee: Double?,
    @SerializedName("showCost")
    val showCost: Double?,
    @SerializedName("taxcost")
    val taxcost: Double?,
    @SerializedName("todayInventoryQty")
    val todayInventoryQty: Int?,
    @SerializedName("tradeKind")
    val tradeKind: Int?,
    @SerializedName("tradeName")
    val tradeName: String?
)