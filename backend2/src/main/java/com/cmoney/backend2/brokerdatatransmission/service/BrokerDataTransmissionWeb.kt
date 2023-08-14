package com.cmoney.backend2.brokerdatatransmission.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokers.BrokerResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.BrokerStockDataResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.brokerdatatransmission.service.api.consents.Consent
import com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey.GetEncryptionKeyResponse

interface BrokerDataTransmissionWeb {

    /**
     * Backend2管理者
     */
    val manager: GlobalBackend2Manager

    /**
     * 取得特定國家的券商資訊列表
     *
     * @param country 國家
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBrokers(
        country: Country,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/brokers"
    ): Result<BrokerResponse>

    /**
     * 取得特定國家下 RSA 公鑰，用於加密使用者帳密等資訊
     *
     * @param country 國家
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getEncryptionKey(
        country: Country,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/encryptionkey"
    ): Result<GetEncryptionKeyResponse>

    /**
     * 驅動 WebSocket 取得庫存帳號 [brokerAccount] 的加密庫存
     *
     * @param brokerAccount 庫存帳號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun fetchTransactionHistory(
        brokerAccount: BrokerAccount,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/fetch/transactionhistory"
    ): Result<Unit>

    /**
     * 取得用戶是否有同意上傳庫存的紀錄
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getUserAgreesImportRecord(
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/useragreesimportrecord"
    ): Result<Boolean>

    /**
     * 取得用戶在特定國家下的庫存資料
     *
     * @param country 國家
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBrokerStockData(
        country: Country,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/brokerstockdata"
    ): Result<List<BrokerStockDataResponse>>

    /**
     * 傳送 [imageRecognitionData] 取得用戶在特定國家下的指定券商庫存資料
     * (不論正確或錯誤，24小時內最多傳送三次)
     *
     * @param country 國家
     * @param imageRecognitionData 圖片辨識資料
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @throws 400 圖片無法辨識/辨識失敗
     *         406 超出圖片上限5張
     *         429 超出使用上限
     *         503 Google API 目前不可用
     */
    suspend fun getBrokerStockDataByImageRecognition(
        country: Country,
        imageRecognitionData: ImageRecognitionData,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/brokerstockdata/imagerecongnition"
    ): Result<ImageRecognitionResponse>

    /**
     * 根據券商資料更新用戶在特定國家下的庫存資料
     *
     * @param country 國家
     * @param brokerData 券商資料
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun putBrokerStockData(
        country: Country,
        brokerData: BrokerData,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/brokerstockdata"
    ): Result<Unit>

    /**
     * 刪除用戶在特定國家下對應券商ID列表下的庫存資料
     *
     * @param country 國家
     * @param brokerIds 券商ID列表
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun deleteBrokerStockData(
        country: Country,
        brokerIds: List<String>,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/api/brokerstockdata"
    ): Result<Unit>

    /**
     * 取得用戶在特定國家下的券商同意書
     *
     * @param country 國家
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getConsents(
        country: Country,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/Consent"
    ): Result<List<Consent>>

    /**
     * 簽署 [brokerId] 券商的同意書
     *
     * @param brokerId 券商ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun signConsent(
        brokerId: String,
        domain: String = manager.getBrokerDataTransmissionSettingAdapter().getDomain(),
        url: String = "${domain}BrokerDataTransmission/Consent/$brokerId"
    ): Result<Unit>

}
