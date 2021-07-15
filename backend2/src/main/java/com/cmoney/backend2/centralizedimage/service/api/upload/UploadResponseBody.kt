package com.cmoney.backend2.centralizedimage.service.api.upload

import com.google.gson.annotations.SerializedName

/**
 * 上傳圖片的回傳結果
 *
 * @property url 圖片網址
 */
data class UploadResponseBody(
    @SerializedName("url")
    val url: String?
)