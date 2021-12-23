package com.cmoney.backend2.imagerecognition.service

import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsResponseBody
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.UsingService

/**
 * 圖片辨識服務
 *
 * @property baseHost url的host
 */
interface ImageRecognitionWeb {

    val baseHost: String

    /**
     * 取得圖片中文字
     *
     * @param host host
     * @param photoBytes 圖片檔案
     * @param expectLength 預期的字串長度，0表示不限制
     * @param usingService 使用的辨識服務，預設為[UsingService.GOO]
     * @return 圖片辨識的結果
     */
    suspend fun getPictureWords(
        host: String = this.baseHost,
        photoBytes: ByteArray,
        expectLength: Int = 0,
        usingService: UsingService = UsingService.GOO
    ): Result<PictureWordsResponseBody>
}