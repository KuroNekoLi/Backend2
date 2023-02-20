package com.cmoney.backend2.virtualtrading2.service.api.getaccount

import com.cmoney.backend2.virtualtrading2.service.api.data.account.response.AccountInfo
import com.google.gson.annotations.SerializedName

/**
 * 取得帳號回應
 *
 * @property content 資料
 *
 */
data class GetAccountResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     *  資料
     *
     * @property accountInfo 帳號資訊
     *
     */
    data class Data(
        @SerializedName("accountInfo")
        val accountInfo: AccountInfo?
    )
}