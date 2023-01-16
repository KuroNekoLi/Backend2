package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate


import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃所有委託單回應
 *
 * @property content 資料
 *
 */
data class GetAllDelegateResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property delegateList 委託單列表
     *
     */
    data class Data(
        @SerializedName("tseOtcOrderByCustomPeriod")
        val delegateList: List<Delegate>?
    ) {
        /**
         * 委託單
         *
         * @property delegateId 委託單號
         * @property accountId 帳號編號
         * @property remainDelegateVolume 剩餘數量
         * @property buySellType 買單: 66 / 賣單: 83
         * @property commodityId 商品編號
         * @property subsistingType 存續種類
         * 立即成交否則取消單(IOC) 73 : 所下委託單必須馬上成交，否則即行取消之委託方式。
         * 立即全部成交否則作廢單(FOK) 70 : 所下委託單必須立即且全部成交，否則即行取消之委託方式。
         * 當日有效單(ROD) 82 : 所下委託單限當日有效。
         * @property reduceVolume 減少數量
         * @property successDealAveragePrice 成交均價
         * @property successDealVolume 成交數量
         * @property groupId 競技場編號
         * @property marginPurchaseAmount 融資金額
         * @property marginPurchaseDownPayment 融資自備款
         * @property memo 備忘錄
         * @property lastModifyDate 最後修改時間
         * @property noteId 筆記編號
         * @property delegatePrice 委託價
         * @property delegateVolume 委託數量
         * @property delegateBehavior 委託單的行為，新增委託單: 73 / 刪除委託單: 68 / 修改委託單: 67
         * @property deliveryPayment 交割款
         * @property serverReceiveDate 服務收到請求時間
         * @property serverReceiveId 服務收到請求編號
         * @property shortSaleCollateral 融券擔保品
         * @property shortSellDeposit 融券保證金
         * @property delegateStatus 委託單狀態
        無定義: 0 / 委託送出: 1 / 委託成功 : 2 / 已成交 : 3 / 部分成交 : 4 / 部分成交，部分取消 : 5
        庫存不足，委託失敗 : 6 / 現金不足，委託失敗 : 7 / 改單成功 : 8 / 已刪單 : 9 / 刪單失敗 : 10
        改單失敗 : 11 / 委託價超過漲跌停價格 : 12 / 停止融券 : 13 / 停止融資 : 14 / 處理中... : 15
        股票不存在或今日停止交易 : 16 / 信用借貸超額 : 17 / 預約單成功 : 20 / 預約單全部取消 : 21
        預約單部份取消 : 22 / 委託結束 : 50 / 減量失敗，無此委託書號 : 151 / 刪單失敗，無此委託書號 : 152
        減量失敗，減量股數大於未成交股數 : 157 / 委託失敗，委託書號重覆 : 160 / 刪單失敗，無剩餘未成交股數 : 161
        預約單轉換錯誤：254 / 系統錯誤，委託失敗 : 255
         * @property marketUnit 整股：1
         * @property targetDelegateId 目標單號
         * @property delegateDate 委託時間
         * @property transactionType 現股: 1 / 融資: 2 / 融券: 3
         *
         */
        data class Delegate(
            @SerializedName("ordNo")
            val delegateId: Long?,
            @SerializedName("account")
            val accountId: Long?,
            @SerializedName("avQty")
            val remainDelegateVolume: Int?,
            @SerializedName("buySellType")
            val buySellType: Int?,
            @SerializedName("commKey")
            val commodityId: String?,
            @SerializedName("condition")
            val subsistingType: Int?,
            @SerializedName("cutQty")
            val reduceVolume: Int?,
            @SerializedName("dealAvgPr")
            val successDealAveragePrice: Double?,
            @SerializedName("dealQty")
            val successDealVolume: Int?,
            @SerializedName("groupId")
            val groupId: Long?,
            @SerializedName("marginCredit")
            val marginPurchaseAmount: Double?,
            @SerializedName("marginOwn")
            val marginPurchaseDownPayment: Double?,
            @SerializedName("memo")
            val memo: String?,
            @SerializedName("modifyTime")
            val lastModifyDate: String?,
            @SerializedName("noteId")
            val noteId: Long?,
            @SerializedName("ordPr")
            val delegatePrice: Double?,
            @SerializedName("ordQty")
            val delegateVolume: Int?,
            @SerializedName("ordType")
            val delegateBehavior: Int?,
            @SerializedName("prePayment")
            val deliveryPayment: Double?,
            @SerializedName("serverRcvNo")
            val serverReceiveDate: Int?,
            @SerializedName("serverRcvTe")
            val serverReceiveId: String?,
            @SerializedName("shortSellingCollateral")
            val shortSaleCollateral: Double?,
            @SerializedName("shortSellingEntrust")
            val shortSellDeposit: Double?,
            @SerializedName("status")
            val delegateStatus: Int?,
            @SerializedName("stockMarketType")
            val marketUnit: Int?,
            @SerializedName("targetOrdNo")
            val targetDelegateId: Long?,
            @SerializedName("tradeTime")
            val delegateDate: String?,
            @SerializedName("tradeType")
            val transactionType: Int?
        )
    }
}