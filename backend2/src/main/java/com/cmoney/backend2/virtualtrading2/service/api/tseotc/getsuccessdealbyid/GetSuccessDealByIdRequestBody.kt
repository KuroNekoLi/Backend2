package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealbyid

import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃特定成交單請求
 *
 * @property query 請求內容
 *
{
    tseOtcDeal(
        accountId: $accountId,
        orderNo: $delegateId
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
data class GetSuccessDealByIdRequestBody(
    @SerializedName("query")
    val query: String
)
