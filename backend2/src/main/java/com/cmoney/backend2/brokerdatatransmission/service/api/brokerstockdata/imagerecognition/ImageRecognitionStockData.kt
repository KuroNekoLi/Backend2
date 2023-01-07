package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition

import com.google.gson.annotations.SerializedName

/**
 * 庫存資料
 *
 * @property stockId 股票代號
 * @property stockInfos 持有股票紀錄
 */
data class ImageRecognitionStockData(
    @SerializedName("StockID")
    val stockId: String?,
    @SerializedName("StockInfos")
    val stockInfos: List<ImageRecognitionStockInfo>?
)
