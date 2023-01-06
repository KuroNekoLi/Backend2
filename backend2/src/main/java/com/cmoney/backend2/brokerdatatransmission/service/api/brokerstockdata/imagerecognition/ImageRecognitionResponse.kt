package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition

/**
 * 圖片辨識回傳物件
 *
 * @property stockData 券商庫存資料
 */
sealed class ImageRecognitionResponse(val stockData: ImageRecognitionResponseBody) {

    /**
     * Response code = 200 -> 解析成功，回傳庫存
     */
    class AllRecognition(stockData: ImageRecognitionResponseBody) : ImageRecognitionResponse(stockData)

    /**
     * Response code = 203 -> 部分解析成功，僅回傳部分庫存
     */
    class PartialRecognition(stockData: ImageRecognitionResponseBody) : ImageRecognitionResponse(stockData)
}
