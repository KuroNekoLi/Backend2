package com.cmoney.backend2.virtualtrading2.service.api.getaccountratio

import com.google.gson.annotations.SerializedName

/**
 * 取得所有帳號請求
 *
 * @property query 請求內容
 *
{
    accountRatios(accountId: $id, mkType: $type, dateCount: $count) {
        account
        dataDe
        funds
        inventoryValues
        isWeekend
        ratio
    }
}
 */
data class GetAccountRatioRequestBody(
    @SerializedName("query")
    val query: String
)
