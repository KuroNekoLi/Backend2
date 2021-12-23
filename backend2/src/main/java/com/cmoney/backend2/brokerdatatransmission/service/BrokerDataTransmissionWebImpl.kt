package com.cmoney.backend2.brokerdatatransmission.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokers.BrokerResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.delete.DeleteBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.BrokerStockDataResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.GetBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.PutBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.consents.Consent
import com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey.GetEncryptionKeyResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.transactionhistory.FetchTransactionHistoryRequest
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.URLEncoder

class BrokerDataTransmissionWebImpl(
    private val service: BrokerDataTransmissionService,
    private val setting: Setting,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : BrokerDataTransmissionWeb {

    override suspend fun getBrokers(country: Country): Result<BrokerResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getBrokers(
                    code = country.isoCode,
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun getEncryptionKey(country: Country): Result<GetEncryptionKeyResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getEncryptionKey(
                    code = country.isoCode,
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun fetchTransactionHistory(brokerAccount: BrokerAccount): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val encodedBrokerAccount = BrokerAccount(
                    brokerId = brokerAccount.brokerId.urlEncode(),
                    subBrokerId = brokerAccount.subBrokerId.urlEncode(),
                    encryptedAccount = brokerAccount.encryptedAccount.urlEncode(),
                    encryptedPassword = brokerAccount.encryptedPassword.urlEncode(),
                    encryptedBirthday = brokerAccount.encryptedBirthday.urlEncode(),
                    encryptedAesKey = brokerAccount.encryptedAesKey.urlEncode(),
                    encryptedAesIv = brokerAccount.encryptedAesIv.urlEncode(),
                )
                service.fetchTransactionHistory(
                    body = FetchTransactionHistoryRequest(
                        guid = setting.identityToken.getMemberGuid(),
                        content = gson.toJson(encodedBrokerAccount)
                    ),
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .handleCustomNoContent(gson)
            }
        }

    override suspend fun getUserAgreesImportRecord(): Result<Boolean> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUserAgreesImportRecord(
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
            }
        }

    override suspend fun getBrokerStockData(country: Country): Result<List<BrokerStockDataResponse>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getBrokerStockData(
                    body = GetBrokerStockDataRequest(
                        code = country.isoCode
                    ),
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                    .toJsonArrayWithErrorResponse()
            }
        }

    override suspend fun putBrokerStockData(
        country: Country,
        brokerData: BrokerData
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.putBrokerStockData(
                    body = PutBrokerStockDataRequest(
                        country.isoCode,
                        setting.identityToken.getMemberGuid(),
                        brokerData
                    ),
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .handleNoContent(gson)
            }
        }

    override suspend fun deleteBrokerStockData(
        country: Country,
        brokerIds: List<String>
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteBrokerStockData(
                    body = DeleteBrokerStockDataRequest(
                        country.isoCode,
                        brokerIds
                    ),
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .handleNoContent(gson)
            }
        }

    override suspend fun getConsents(country: Country): Result<List<Consent>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getConsents(
                    code = country.isoCode,
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                    .toJsonArrayWithErrorResponse()
            }
        }

    override suspend fun signConsent(brokerId: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.signConsent(
                    brokerId = brokerId,
                    authToken = setting.accessToken.createAuthorizationBearer()
                )
                    .handleNoContent(gson)
            }
        }

    /**
     * 處理正常回傳是JsonArray 發生錯誤是JsonObject 的api回傳
     *
     * @param T
     * @return
     */
    @Throws(ServerException::class)
    private inline fun <reified T> JsonElement.toJsonArrayWithErrorResponse(): T {
        val responseResult = if (this.isJsonArray) {
            try {
                gson.fromJson<T>(this, object : TypeToken<T>() {}.type)
            } catch (exception: JsonSyntaxException) {
                null
            }
        } else {
            null
        }

        return if (responseResult != null) {
            responseResult
        } else {
            val error = gson.fromJson<CMoneyError>(this, object : TypeToken<CMoneyError>() {}.type)
            throw ServerException(
                error.detail?.code ?: Constant.SERVICE_ERROR_CODE,
                error.detail?.message.orEmpty()
            )
        }
    }

    private fun String.urlEncode(): String {
        return URLEncoder.encode(this, Charsets.UTF_8.name())
    }

    @Throws(ServerException::class, HttpException::class)
    private fun <T : Response<Void>> T.handleCustomNoContent(gson: Gson) {
        return when {
            code() == 204 -> {
                //不處理任何Body
            }
            code() == 400 -> {
                throw parseCustomServerException(gson)
            }
            else -> {
                throw HttpException(this)
            }
        }
    }

    private fun <T : Response<Void>> T.parseCustomServerException(gson: Gson): ServerException {
        val errorBody = errorBody()?.string()

        val json = gson.fromJson(errorBody, JsonObject::class.java)
        val customErrorDetail = gson.fromJson(
            json?.get("body")?.toString(),
            CMoneyError.Detail::class.java
        )
        if (!customErrorDetail?.message.isNullOrEmpty() || customErrorDetail?.code != null) {
            // has message or code
            return ServerException(
                code = customErrorDetail?.code ?: Constant.SERVICE_ERROR_CODE,
                message = customErrorDetail?.message.orEmpty()
            )
        }

        val cmoneyError = gson.fromJson(
            errorBody,
            CMoneyError::class.java
        )
        return if (cmoneyError.message.isNullOrEmpty()) {
            ServerException(
                code = cmoneyError.detail?.code ?: Constant.SERVICE_ERROR_CODE,
                message = cmoneyError.detail?.message.orEmpty()
            )
        } else {
            ServerException(
                code = Constant.SERVICE_NOT_SUPPORT_ERROR_CODE,
                message = cmoneyError.message.orEmpty()
            )
        }
    }

}
