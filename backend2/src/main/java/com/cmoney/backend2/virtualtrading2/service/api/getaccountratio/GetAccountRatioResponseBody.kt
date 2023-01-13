package com.cmoney.backend2.virtualtrading2.service.api.getaccountratio


import com.google.gson.annotations.SerializedName

/**
 * 取得帳號報酬率
 *
 * @property content 資料
 *
 */
data class GetAccountRatioResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * Data 資料
     *
     * @property accountRatios 帳號報酬率
     *
     */
    data class Data(
        @SerializedName("accountRatios")
        val accountRatios: List<AccountRatio>?
    ) {
        /**
         * 帳號報酬率
         *
         * @property accountId 帳號編號
         * @property date 日期
         * @property funds 現金資產
         * @property inventoryValues 庫存資產
         * @property isWeekend 是否為週末日
         * @property roi 投資報酬率
         *
         */
        data class AccountRatio(
            @SerializedName("account")
            val accountId: Int?,
            @SerializedName("dataDe")
            val date: String?,
            @SerializedName("funds")
            val funds: Double?,
            @SerializedName("inventoryValues")
            val inventoryValues: Double?,
            @SerializedName("isWeekend")
            val isWeekend: Boolean?,
            @SerializedName("ratio")
            val roi: Double?
        )
    }
}