package com.cmoney.backend2.imagerecognition.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsResponseBody
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.UsingService

/**
 * 圖片辨識服務
 */
interface ImageRecognitionWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得圖片中文字
     *
     * @param photoBytes 圖片檔案
     * @param expectLength 預期的字串長度，0表示不限制
     * @param usingService 使用的辨識服務，預設為[UsingService.GOO]
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 圖片辨識的結果
     */
    suspend fun getPictureWords(
        photoBytes: ByteArray,
        expectLength: Int = 0,
        usingService: UsingService = UsingService.GOO,
        domain: String = manager.getImageRecognitionSettingAdapter().getDomain(),
        url: String = "${domain}ImageRecognitionService/getpicturewords"
    ): Result<PictureWordsResponseBody>
}