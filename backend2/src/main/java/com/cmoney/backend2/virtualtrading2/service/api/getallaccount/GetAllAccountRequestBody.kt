package com.cmoney.backend2.virtualtrading2.service.api.getallaccount

import com.google.gson.annotations.SerializedName

/**
 * 取得所有帳號請求
 *
 * @property query 請求內容
 *
 *
{
    allAccountInfo {
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
data class GetAllAccountRequestBody(
    @SerializedName("query")
    val query: String
)
