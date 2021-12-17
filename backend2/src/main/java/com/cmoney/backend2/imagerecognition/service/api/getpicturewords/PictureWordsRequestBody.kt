package com.cmoney.backend2.imagerecognition.service.api.getpicturewords

import com.google.gson.annotations.SerializedName

/**
 * 圖片字串辨識要求
 *
 * @property verifyLength 設定預期字串長度，0表示不限制
 * @property usingService 選擇使用的服務，目前提供四種:GOO、OCR、CCD、FBS，預設為GOO
 * @property pictureContent 請將圖片使用Base64轉換成字串
 */
data class PictureWordsRequestBody(
    @SerializedName("verifyLength")
    val verifyLength: Int,
    @SerializedName("usingService")
    val usingService: String,
    @SerializedName("pictureContent")
    val pictureContent: String
)