package com.cmoney.backend2.brokerdatatransmission.service.api

import com.google.gson.annotations.SerializedName

/**
 * 要取得庫存資料的帳號參數
 *
 * @property brokerId 券商Id
 * @property subBrokerId 券商分點Id，不需要則帶入空字串
 * @property encryptedAccount 以服務公鑰加密過的券商帳號再以 Base64 編碼
 * @property encryptedPassword 以服務公鑰加密過的券商密碼再以 Base64 編碼
 * @property encryptedBirthday 以服務公鑰加密過的帳號生日再以 Base64 編碼，不需要則加密空字串
 * @property encryptedAesKey 以服務公鑰加密過的客端的 AES 金鑰再以 Base64 編碼
 * @property encryptedAesIv 以服務公鑰加密過的客端的 AES IV 再以 Base64 編碼
 */
class BrokerAccount(
    @SerializedName("brokerId")
    val brokerId: String,
    @SerializedName("subBrokerId")
    val subBrokerId: String,
    @SerializedName("account")
    val encryptedAccount: String,
    @SerializedName("password")
    val encryptedPassword: String,
    @SerializedName("birthday")
    val encryptedBirthday: String,
    @SerializedName("key")
    val encryptedAesKey: String,
    @SerializedName("extraParameter")
    val encryptedAesIv: String,
)
