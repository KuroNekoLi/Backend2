package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal

import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃所有成交單請求
 *
 * @property query 請求內容
 *
 * tradeType：ALL : 0 / 現股: 1 / 融資: 2 / 融券: 3
 *
{
    tseOtcDealByCustomPeriod(
        accountId: $id,
        beginTime: "yyyy/MM/dd",
        endTime: "yyyy/MM/dd",
        tradeType: $type
    ) {
        te
        account
        ordNo
        stockMarketType
        tradeType
        buySellType
        commKey
        dealPr
        dealQty
        fee
        tax
        dealTno
        flag
        sn
        shortSellingFee
        memo
        actualCost
        borrow
        bsAvgPr
        remainQty
        isSuccess
    }
}
 */
data class GetAllSuccessDealRequestBody(
    @SerializedName("query")
    val query: String
)
