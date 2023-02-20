package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory

import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃庫存請求(查詢上市櫃在倉部位)
 *
 * @property query 請求內容
 *
{
    tseOtcPosition(accountId: $accountId) {
        account
        bs
        canOrdQty
        commKey
        commName
        cost
        createTime
        dealAvgPr
        incomeLoss
        incomeLossWithoutPreFee
        inventoryQty
        nowPr
        ratio
        shortSellingFee
        showCost
        taxCost
        todayInventoryQty
        tradeName
        tradeType
    }
}
 */
data class GetAllInventoryRequestBody(
    @SerializedName("query")
    val query: String
)
