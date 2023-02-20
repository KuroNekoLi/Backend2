package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal


import com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.successdeal.SuccessDealOrder
import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃所有成交單回應
 *
 * @property content 資料
 *
 */
data class GetAllSuccessDealResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property successDealList 成交列表
     *
     */
    data class Data(
        @SerializedName("tseOtcDealByCustomPeriod")
        val successDealList: List<SuccessDealOrder>?
    )
}