package com.cmoney.backend2.virtualtrading2.service.api.tseotc.gettodayalldelegate


import com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.delegate.DelegateOrder
import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃今日所有委託單回應
 *
 * @property content 資料
 *
 */
data class GetTodayAllDelegateResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property delegateList 委託單列表
     *
     */
    data class Data(
        @SerializedName("todayTseOtcOrder")
        val delegateList: List<DelegateOrder>?
    )
}