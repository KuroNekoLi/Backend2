package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealdetail


import com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.successdeal.SuccessDealOrder
import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃成交單細節回應
 *
 * @property content 資料
 *
 */
data class GetSuccessDealDetailResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property delegate 委託單
     *
     */
    data class Data(
        @SerializedName("tseOtcDeal")
        val delegate: SuccessDealOrder?
    )
}