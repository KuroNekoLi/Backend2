package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.google.gson.annotations.SerializedName

/**
 * 券商庫存資料
 *
 * @property brokerId 券商代號
 * @property brokerShortName 券商名稱
 * @property subBrokerId 分點代號
 * @property updateTimeOfUnixTimeSeconds 更新時間
 * @property inStockData 庫存紀錄
 */
data class BrokerStockDataResponse(
    @SerializedName("brokerId")
    val brokerId: String?,
    @SerializedName("brokerShortName")
    val brokerShortName: String?,
    @SerializedName("subBrokerId")
    val subBrokerId: String?,
    @SerializedName("updateTimeOfUnixTimeSeconds")
    val updateTimeOfUnixTimeSeconds: Long?,
    @SerializedName("inStockData")
    val inStockData: List<StockData>?
)
