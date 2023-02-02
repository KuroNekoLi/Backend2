package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory


import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃庫存回應
 *
 * @property content 資料
 *
 */
data class GetInventoryResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property inventoryList 庫存列表
     *
     */
    data class Data(
        @SerializedName("tseOtcPosition")
        val inventoryList: List<Inventory>?
    ) {
        /**
         * 即時庫存
         *
         * @property accountId 帳號編號
         * @property buySellType 買賣別
         * @property canDelegateVolume 可委託量
         * @property commodityId 商品資訊
         * @property commodityName 商品名稱
         * @property cost 成本
         * @property createTime 建立時間
         * @property successDealAveragePrice 成交均價
         * @property gainLoss 損益
         * @property gainLossWithoutFee 損益(不含預扣稅額)
         * @property inventoryVolume 庫存數量
         * @property nowPrice 現價
         * @property ror 報酬率
         * @property shortSaleFee 借券費
         * @property totalDownPayment 總自備款(現股買入市值、融資自備款、融券保證金)
         * @property transactionTax 交易稅
         * @property todaySuccessDealInventoryVolume 今日成交庫存數量
         * @property transactionTypeName 交易類別名稱
         * @property transactionType 交易類型，現股: 1 / 融資: 2 / 融券: 3
         *
         */
        data class Inventory(
            @SerializedName("account")
            val accountId: Long?,
            @SerializedName("bs")
            val buySellType: Int?,
            @SerializedName("canOrdQty")
            val canDelegateVolume: Int?,
            @SerializedName("commKey")
            val commodityId: String?,
            @SerializedName("commName")
            val commodityName: String?,
            @SerializedName("cost")
            val cost: String?,
            @SerializedName("createTime")
            val createTime: Long?,
            @SerializedName("dealAvgPr")
            val successDealAveragePrice: String?,
            @SerializedName("incomeLoss")
            val gainLoss: String?,
            @SerializedName("incomeLossWithoutPreFee")
            val gainLossWithoutFee: String?,
            @SerializedName("inventoryQty")
            val inventoryVolume: Int?,
            @SerializedName("nowPr")
            val nowPrice: String?,
            @SerializedName("ratio")
            val ror: String?,
            @SerializedName("shortSellingFee")
            val shortSaleFee: String?,
            @SerializedName("showCost")
            val totalDownPayment: String?,
            @SerializedName("taxCost")
            val transactionTax: String?,
            @SerializedName("todayInventoryQty")
            val todaySuccessDealInventoryVolume: Int?,
            @SerializedName("tradeName")
            val transactionTypeName: String?,
            @SerializedName("tradeType")
            val transactionType: Int?
        )
    }
}