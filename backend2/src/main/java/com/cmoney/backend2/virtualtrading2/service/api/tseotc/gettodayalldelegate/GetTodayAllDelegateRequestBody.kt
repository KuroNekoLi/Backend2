package com.cmoney.backend2.virtualtrading2.service.api.tseotc.gettodayalldelegate

import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃今日所有委託單
 *
 * @property query 請求內容
 *
 * tradeType：ALL : 0 / 現股: 1 / 融資: 2 / 融券: 3
{
    todayTseOtcOrder(
        accountId: $id,
        tradeType: $type
    ) {
        ordNo
        targetOrdNo
        account
        groupId
        tradeTime
        status
        ordType
        condition
        tradeType
        stockMarketType
        buySellType
        commKey
        ordPr
        ordQty
        dealAvgPr
        dealQty
        avQty
        cutQty
        prePayment
        serverRcvTe
        serverRcvNo
        marginCredit
        marginOwn
        shortSellingCollateral
        shortSellingEntrust
        memo
        noteId
        modifyTime
    }
}
 */
data class GetTodayAllDelegateRequestBody(
    @SerializedName("query")
    val query: String
)
