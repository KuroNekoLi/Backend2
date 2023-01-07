package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition

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
data class ImageRecognitionResponseBody(
    @SerializedName("BrokerId")
    val brokerId: String?,
    @SerializedName("BrokerShortName")
    val brokerShortName: String?,
    @SerializedName("SubBrokerId")
    val subBrokerId: String?,
    @SerializedName("UpdateTimeOfUnixTimeSeconds")
    val updateTimeOfUnixTimeSeconds: Long?,
    @SerializedName("InStockData")
    val inStockData: List<ImageRecognitionStockData>?
)
