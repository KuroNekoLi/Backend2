package com.cmoney.backend2.imagerecognition.service.api.getpicturewords

import com.google.gson.annotations.SerializedName

/**
 * 圖片文字辨識回傳
 *
 * @property result 辨識結果文字
 */
data class PictureWordsResponseBody(
    @SerializedName("result")
    val result: String?
)