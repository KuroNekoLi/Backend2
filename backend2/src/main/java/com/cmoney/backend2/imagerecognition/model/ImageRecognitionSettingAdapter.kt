package com.cmoney.backend2.imagerecognition.model


/**
 * 圖像辨識服務設定轉接器
 */
interface ImageRecognitionSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}