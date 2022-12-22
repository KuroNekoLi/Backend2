package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition

import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.google.gson.annotations.SerializedName

/**
 * 圖片辨識 Request 物件
 *
 * @property countryISOCode 國家 ISO 代碼，見 [Country]
 * @property brokerId 券商代碼
 * @property subBrokerId 分點代碼
 * @property encryptedStockDataImages 經過客端 AES 加密的券商庫存圖片
 * @property encryptedAesKey 以服務公鑰加密過的客端的 AES 金鑰再以 Base64 編碼
 * @property encryptedAesIv 以服務公鑰加密過的客端的 AES 金鑰再以 Base64 編碼
 */
data class ImageRecognitionRequest(
    @SerializedName("countryISOCode")
    val countryISOCode: Int,
    @SerializedName("brokerId")
    val brokerId: String,
    @SerializedName("subBrokerId")
    val subBrokerId: String,
    @SerializedName("stockDataImages")
    val encryptedStockDataImages: List<String>,
    @SerializedName("encryptedKey")
    val encryptedAesKey: String,
    @SerializedName("encryptedIV")
    val encryptedAesIv: String
)
