package com.cmoney.backend2.virtualtrading2.service.api.getallaccount

import com.cmoney.backend2.virtualtrading2.service.api.data.account.response.AccountInfo
import com.google.gson.annotations.SerializedName

/**
 * 取得所有帳號回應
 *
 * @property content 資料
 *
 */
data class GetAllAccountResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property allAccountInfo 所有帳號
     *
     */
    data class Data(
        @SerializedName("allAccountInfo")
        val allAccountInfo: List<AccountInfo>?
    )
}