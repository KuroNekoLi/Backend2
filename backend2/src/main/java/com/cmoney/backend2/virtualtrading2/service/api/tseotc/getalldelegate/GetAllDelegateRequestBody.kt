package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate

import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃所有委託單
 *
 * @property query 請求內容
 *
 *
{
    tseOtcOrderByCustomPeriod(
        accountId: $id,
        beginTime: "yyyy/MM/dd",
        endTime: "yyyy/MM/dd",
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
data class GetAllDelegateRequestBody(
    @SerializedName("query")
    val query: String
)
