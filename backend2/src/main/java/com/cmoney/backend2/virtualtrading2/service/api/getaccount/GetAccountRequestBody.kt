package com.cmoney.backend2.virtualtrading2.service.api.getaccount

import com.google.gson.annotations.SerializedName

/**
 * 取得特定帳號請求
 *
 * @property query 請求內容
 * accountId：指定帳號
 *
{
    accountInfo(accountId: $id) {
        account
        accountPayType
        accountType
        avgMonthOrderCount
        borrowFunds
        borrowLimit
        canWatch
        createTime
        defaultFunds
        extendFunds
        funds
        groupId
        isDefault
        isDelete
        isEmail
        maxReadSn
        memberId
        name
        needFee
        needTax
        optIncomeLoss
        stockIncomeLoss
        tmxIncomeLoss
        totalPunishment
        tradedWarrantDate
        updateTime
        viewTime
        warrantIncomeLoss
    }
}
 */
data class GetAccountRequestBody(
    @SerializedName("query")
    val query: String
)
