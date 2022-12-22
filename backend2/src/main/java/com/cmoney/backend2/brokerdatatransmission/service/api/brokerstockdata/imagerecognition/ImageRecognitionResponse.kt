package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.BrokerStockDataResponse

/**
 * 圖片辨識回傳物件
 *
 * @property stockData 券商庫存資料
 */
sealed class ImageRecognitionResponse(val stockData: BrokerStockDataResponse) {

    /**
     * Response code = 200 -> 解析成功，回傳庫存
     */
    class AllRecognition(stockData: BrokerStockDataResponse): ImageRecognitionResponse(stockData)

    /**
     * Response code = 203 -> 部分解析成功，僅回傳部分庫存
     */
    class PartialRecognition(stockData: BrokerStockDataResponse): ImageRecognitionResponse(stockData)
}
