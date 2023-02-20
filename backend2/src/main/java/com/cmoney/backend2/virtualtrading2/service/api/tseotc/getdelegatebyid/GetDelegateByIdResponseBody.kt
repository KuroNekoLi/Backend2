package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatebyid


import com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.delegate.DelegateOrder
import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃特定委託單回應
 *
 * @property content 資料
 *
 */
data class GetDelegateByIdResponseBody(
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
        @SerializedName("tseOtcOrder")
        val delegate: DelegateOrder?
    )
}