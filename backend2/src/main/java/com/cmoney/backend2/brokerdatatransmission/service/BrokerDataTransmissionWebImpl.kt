package com.cmoney.backend2.brokerdatatransmission.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.extension.parseServerException
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toJsonArrayWithErrorResponse
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokers.BrokerResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.delete.DeleteBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.BrokerStockDataResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.GetBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.PutBrokerStockDataRequest
import com.cmoney.backend2.brokerdatatransmission.service.api.consents.Consent
import com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey.GetEncryptionKeyResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.transactionhistory.FetchTransactionHistoryRequest
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.URLEncoder

class BrokerDataTransmissionWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: BrokerDataTransmissionService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : BrokerDataTransmissionWeb {

    override suspend fun getBrokers(
        country: Country,
        domain: String,
        url: String
    ): Result<BrokerResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getBrokers(
                    url = url,
                    code = country.isoCode,
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun getEncryptionKey(
        country: Country,
        domain: String,
        url: String
    ): Result<GetEncryptionKeyResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getEncryptionKey(
                    url = url,
                    code = country.isoCode,
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun fetchTransactionHistory(
        brokerAccount: BrokerAccount,
        domain: String,
        url: String
    ): Result<Unit> =
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
                    url = url,
                    body = FetchTransactionHistoryRequest(
                        guid = manager.getIdentityToken().getMemberGuid(),
                        content = gson.toJson(encodedBrokerAccount)
                    ),
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .handleCustomNoContent(gson)
            }
        }

    override suspend fun getUserAgreesImportRecord(domain: String, url: String): Result<Boolean> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUserAgreesImportRecord(
                    url = url,
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
            }
        }

    override suspend fun getBrokerStockData(
        country: Country,
        domain: String,
        url: String
    ): Result<List<BrokerStockDataResponse>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getBrokerStockData(
                    url = url,
                    body = GetBrokerStockDataRequest(
                        code = country.isoCode
                    ),
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .checkResponseBody(gson = gson)
                    .toJsonArrayWithErrorResponse(gson = gson)
            }
        }

    override suspend fun getBrokerStockDataByImageRecognition(
        country: Country,
        imageRecognitionData: ImageRecognitionData,
        domain: String,
        url: String
    ): Result<ImageRecognitionResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getBrokerStockDataByImageRecognition(
                url = url,
                body = ImageRecognitionRequest(
                    countryISOCode = country.isoCode,
                    brokerId = imageRecognitionData.brokerId,
                    subBrokerId = imageRecognitionData.subBrokerId,
                    encryptedStockDataImages = imageRecognitionData.encryptedStockDataImages,
                    encryptedAesKey = imageRecognitionData.encryptedAesKey.urlEncode(),
                    encryptedAesIv = imageRecognitionData.encryptedAesIv.urlEncode()
                ),
                authToken = manager.getAccessToken().createAuthorizationBearer()
            ).let { response ->
                when (response.code()) {
                    200 -> ImageRecognitionResponse.AllRecognition(response.requireBody())
                    206 -> ImageRecognitionResponse.PartialRecognition(response.requireBody())
                    400 -> throw response.parseServerException(gson)
                    else -> throw HttpException(response)
                }
            }
        }
    }

    override suspend fun putBrokerStockData(
        country: Country,
        brokerData: BrokerData,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.putBrokerStockData(
                    url = url,
                    body = PutBrokerStockDataRequest(
                        country.isoCode,
                        manager.getIdentityToken().getMemberGuid(),
                        brokerData
                    ),
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .handleNoContent(gson)
            }
        }

    override suspend fun deleteBrokerStockData(
        country: Country,
        brokerIds: List<String>,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteBrokerStockData(
                    url = url,
                    body = DeleteBrokerStockDataRequest(
                        country.isoCode,
                        brokerIds
                    ),
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .handleNoContent(gson)
            }
        }

    override suspend fun getConsents(
        country: Country,
        domain: String,
        url: String
    ): Result<List<Consent>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getConsents(
                    url = url,
                    code = country.isoCode,
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                    .toJsonArrayWithErrorResponse(gson = gson)
            }
        }

    override suspend fun signConsent(brokerId: String, domain: String, url: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.signConsent(
                    url = url,
                    authToken = manager.getAccessToken().createAuthorizationBearer()
                )
                    .handleNoContent(gson)
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
