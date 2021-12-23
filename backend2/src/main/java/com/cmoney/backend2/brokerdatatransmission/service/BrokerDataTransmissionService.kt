package com.cmoney.backend2.brokerdatatransmission.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.brokerdatatransmission.service.api.brokers.BrokerResponseWithError
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.delete.DeleteBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.GetBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.PutBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey.GetEncryptionKeyResponseWithError
import com.cmoney.backend2.brokerdatatransmission.service.api.transactionhistory.FetchTransactionHistoryRequest
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

/**
 * BrokerDataTransmission 券商庫存服務
 * @see <a href="https://docs.google.com/document/d/1xeVOusM35YCUeLCEtCzGnljx9DwH4XmG7pzPyjj4o1E/edit#">API</a>
 * @see <a href="https://docs.google.com/document/d/1g81zXlrxqDEr-LEMeuuOP2r79W84HcpPi6YrbxO0oqQ/edit">All Response</a>
 * @see <a href="https://docs.google.com/document/d/1zoh2wVSAyiF78tx9nOlTxqVhTaukusUAGWnqpqkDWkA/edit?ts=5e5ce0c1#heading=h.zc2t4sdsdt6i">後端流程</a>
 * @see <a href="https://docs.google.com/spreadsheets/d/1Fj27XcFLmJgzRQSddlxxh2DVEbW-ISnzWZuiTJ_KS3k/edit#gid=1434418544">測試帳號</a>
 * @see <a href="https://outpost.cmoney.tw/brokerdatatransmission/swagger/index.html">Swagger</a>
 */
interface BrokerDataTransmissionService {

    @RecordApi
    @POST("BrokerDataTransmission/api/brokers")
    suspend fun getBrokers(
        @Query("code") code: Int,
        @Header("Authorization") authToken: String
    ): Response<BrokerResponseWithError>

    @RecordApi
    @POST("BrokerDataTransmission/api/encryptionkey")
    suspend fun getEncryptionKey(
        @Query("code") code: Int,
        @Header("Authorization") authToken: String
    ): Response<GetEncryptionKeyResponseWithError>

    @RecordApi
    @POST("BrokerDataTransmission/api/fetch/transactionhistory")
    suspend fun fetchTransactionHistory(
        @Body body: FetchTransactionHistoryRequest,
        @Header("Authorization") authToken: String
    ): Response<Void>

    @RecordApi
    @GET("BrokerDataTransmission/api/useragreesimportrecord")
    suspend fun getUserAgreesImportRecord(
        @Header("Authorization") authToken: String
    ): Response<Boolean>

    @RecordApi
    @POST("BrokerDataTransmission/api/brokerstockdata")
    suspend fun getBrokerStockData(
        @Body body: GetBrokerStockDataRequest,
        @Header("Authorization") authToken: String
    ): Response<JsonElement>

    @RecordApi
    @PUT("BrokerDataTransmission/api/brokerstockdata")
    suspend fun putBrokerStockData(
        @Body body: PutBrokerStockDataRequest,
        @Header("Authorization") authToken: String
    ): Response<Void>

    @RecordApi
    @HTTP(method = "DELETE", path = "BrokerDataTransmission/api/brokerstockdata", hasBody = true)
    suspend fun deleteBrokerStockData(
        @Body body: DeleteBrokerStockDataRequest,
        @Header("Authorization") authToken: String
    ): Response<Void>

    @RecordApi
    @GET("BrokerDataTransmission/Consent")
    suspend fun getConsents(
        @Query("code") code: Int,
        @Header("Authorization") authToken: String
    ): Response<JsonElement>

    @RecordApi
    @PUT("BrokerDataTransmission/Consent/{brokerId}")
    suspend fun signConsent(
        @Path("brokerId") brokerId: String,
        @Header("Authorization") authToken: String
    ): Response<Void>

}
