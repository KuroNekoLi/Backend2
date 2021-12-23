package com.cmoney.backend2.brokerdatatransmission.service

import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokers.BrokerResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.BrokerStockDataResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.brokerdatatransmission.service.api.consents.Consent
import com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey.GetEncryptionKeyResponse

interface BrokerDataTransmissionWeb {

    val baseHost: String

    /**
     * 取得 [country] 下的券商資訊列表
     */
    suspend fun getBrokers(country: Country, host: String = this.baseHost): Result<BrokerResponse>

    /**
     * 取得 [country] 下的 RSA 公鑰，用於加密使用者帳密等資訊
     */
    suspend fun getEncryptionKey(
        country: Country,
        host: String = this.baseHost
    ): Result<GetEncryptionKeyResponse>

    /**
     * 驅動 WebSocket 取得庫存帳號 [brokerAccount] 的加密庫存
     */
    suspend fun fetchTransactionHistory(
        brokerAccount: BrokerAccount,
        host: String = this.baseHost
    ): Result<Unit>

    /**
     * 取得用戶是否有同意上傳庫存的紀錄
     */
    suspend fun getUserAgreesImportRecord(host: String = this.baseHost): Result<Boolean>

    /**
     * 取得用戶在 [country] 下的庫存資料
     */
    suspend fun getBrokerStockData(
        country: Country,
        host: String = this.baseHost
    ): Result<List<BrokerStockDataResponse>>

    /**
     * 以 [brokerData] 更新用戶在 [country] 下的庫存資料
     */
    suspend fun putBrokerStockData(
        country: Country,
        brokerData: BrokerData,
        host: String = this.baseHost
    ): Result<Unit>

    /**
     * 刪除用戶在 [country] 下對應券商ID列表 [brokerIds] 下的庫存資料
     */
    suspend fun deleteBrokerStockData(
        country: Country,
        brokerIds: List<String>,
        host: String = this.baseHost
    ): Result<Unit>

    /**
     * 取得用戶在 [country] 下的券商同意書
     */
    suspend fun getConsents(country: Country, host: String = this.baseHost): Result<List<Consent>>

    /**
     * 簽署 [brokerId] 券商的同意書
     */
    suspend fun signConsent(brokerId: String, host: String = this.baseHost): Result<Unit>

}
