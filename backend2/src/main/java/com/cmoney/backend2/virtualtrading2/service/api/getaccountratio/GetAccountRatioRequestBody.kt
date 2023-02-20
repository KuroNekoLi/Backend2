package com.cmoney.backend2.virtualtrading2.service.api.getaccountratio

import com.google.gson.annotations.SerializedName

/**
 * 取得所有帳號請求
 *
 * @property query 請求內容
 *
 * accountId：指定帳號
 * mkType：股市(個股+權證):1 / 期權(期貨 + 選擇權):2 / 個股:3 / 權證:4 / 期貨:5 / 選擇權:6 / 混合市場:7
 * dateCount：取得天數
 *
{
    accountRatios(
        accountId: $id,
        mkType: $type,
        dateCount: $count
    ) {
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
