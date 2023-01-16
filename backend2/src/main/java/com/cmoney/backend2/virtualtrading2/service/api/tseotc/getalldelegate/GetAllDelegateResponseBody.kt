package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate


import com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.delegate.DelegateOrder
import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃所有委託單回應
 *
 * @property content 資料
 *
 */
data class GetAllDelegateResponseBody(
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
        @SerializedName("tseOtcOrderByCustomPeriod")
        val delegateList: List<DelegateOrder>?
    )
}