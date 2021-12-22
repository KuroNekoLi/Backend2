package com.cmoney.backend2.brokerdatatransmission.service

import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokers.BrokerResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.BrokerStockDataResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.brokerdatatransmission.service.api.consents.Consent
import com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey.GetEncryptionKeyResponse

interface BrokerDataTransmissionWeb {

    /**
     * 取得 [country] 下的券商資訊列表
     */
    suspend fun getBrokers(country: Country): Result<BrokerResponse>

    /**
     * 取得 [country] 下的 RSA 公鑰，用於加密使用者帳密等資訊
     */
    suspend fun getEncryptionKey(country: Country): Result<GetEncryptionKeyResponse>

    /**
     * 驅動 WebSocket 取得庫存帳號 [brokerAccount] 的加密庫存
     */
    suspend fun fetchTransactionHistory(brokerAccount: BrokerAccount): Result<Unit>

    /**
     * 取得用戶在 [country] 下的庫存資料
     */
    suspend fun getBrokerStockData(country: Country): Result<BrokerStockDataResponse>

    /**
     * 以 [brokerData] 更新用戶在 [country] 下的庫存資料
     */
    suspend fun putBrokerStockData(country: Country, brokerData: BrokerData): Result<Unit>

    /**
     * 取得用戶在 [country] 下的券商同意書
     */
    suspend fun getConsents(country: Country): Result<List<Consent>>

    /**
     * 簽署 [brokerId] 券商的同意書
     */
    suspend fun signConsent(brokerId: String): Result<Unit>

}
