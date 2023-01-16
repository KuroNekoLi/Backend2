package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatedetail

import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃委託單細節請求
 *
 * @property query 請求內容
 *
 *
{
    tseOtcOrder(accountId: $accountId, orderNo: $delegateId) {
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
data class GetDelegateDetailRequestBody(
    @SerializedName("query")
    val query: String
)
