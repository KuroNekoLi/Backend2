package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealbyid


import com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.successdeal.SuccessDealOrder
import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃特定成交單回應
 *
 * @property content 資料
 *
 */
data class GetSuccessDealByIdResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property successDealOrder 成交單
     *
     */
    data class Data(
        @SerializedName("tseOtcDeal")
        val successDealOrder: SuccessDealOrder?
    )
}