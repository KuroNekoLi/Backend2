package com.cmoney.backend2.billing.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.billing.service.api.getdevelpoerpayload.GetDeveloperPayloadRequestBody
import com.cmoney.backend2.billing.service.api.getproductinfo.GetProductInfoRequestBody
import com.cmoney.backend2.billing.service.api.getproductinfo.ProductInformation
import com.cmoney.backend2.billing.service.api.isPurchasable.IsPurchasableRequestBody
import com.cmoney.backend2.billing.service.api.recoverygoogleinappreceipt.RecoveryGoogleInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.recoverygooglesubreceipt.RecoveryGoogleSubReceiptRequestBody
import com.cmoney.backend2.billing.service.api.recoveryhuaweiinappreceipt.RecoveryHuaweiInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.recoveryhuaweisubreceipt.RecoveryHuaweiSubReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifygoogleinappreceipt.VerifyGoogleInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifygooglesubreceipt.VerifyGoogleSubReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifyhuaweiinappreceipt.VerifyHuaweiInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifyhuaweisubreceipt.VerifyHuaweiSubReceiptRequestBody
import com.cmoney.backend2.billing.service.common.*
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.withContext

class BillingWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: BillingService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : BillingWeb {

    override suspend fun getDeveloperPayload(domain: String, url: String): Result<Long> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = GetDeveloperPayloadRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                val response = service.getDeveloperPayload(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = requestBody
                )
                val responseBody = response.checkResponseBody(gson)
                responseBody.id ?: -1
            }
        }

    override suspend fun isReadyToPurchase(domain: String, url: String): Result<Boolean> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = IsPurchasableRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                val response = service.isReadyToPurchase(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = requestBody
                )
                val responseBody = response.checkResponseBody(gson)
                responseBody.isPurchasable ?: false
            }
        }

    override suspend fun getProductInfo(
        domain: String,
        url: String
    ): Result<List<ProductInformation>> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = GetProductInfoRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                val response = service.getIapProductInformation(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = requestBody
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun getAuthStatus(domain: String, url: String): Result<Authorization> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getAuthStatus(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            val responseBody = response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
            Authorization(
                responseBody.authType,
                responseBody.authExpTime
            )
        }
    }

    override suspend fun getTargetAppAuthStatus(
        queryAppId: Int,
        domain: String,
        url: String
    ): Result<Authorization> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getTargetAppAuthStatus(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    queryAppId = queryAppId
                )
                val responseBody = response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
                Authorization(
                    responseBody.authType,
                    responseBody.authExpTime
                )
            }
        }

    override suspend fun verifyHuaweiInAppReceipt(
        receipt: InAppHuaweiReceipt,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = VerifyHuaweiInAppReceiptRequestBody(
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                receipt = receipt
            )
            val response = service.verifyHuaweiInAppReceipt(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun verifyHuaweiSubReceipt(
        receipt: SubHuaweiReceipt,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = VerifyHuaweiSubReceiptRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    receipt = receipt
                )
                val response = service.verifyHuaweiSubReceipt(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = requestBody
                )
                response.handleNoContent(gson)
            }
        }

    override suspend fun recoveryHuaweiInAppReceipt(
        receipts: List<InAppHuaweiReceipt>,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = RecoveryHuaweiInAppReceiptRequestBody(
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                receipts = receipts
            )
            val response = service.recoveryHuaweiInAppReceipt(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            )
            return@runCatching response.handleNoContent(gson)
        }
    }

    override suspend fun recoveryHuaweiSubReceipt(
        receipts: List<SubHuaweiReceipt>,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = RecoveryHuaweiSubReceiptRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    receipts = receipts
                )
                val response = service.recoveryHuaweiSubReceipt(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = requestBody
                )
                response.handleNoContent(gson)
            }
        }

    override suspend fun verifyGoogleInAppReceipt(
        receipt: InAppGoogleReceipt,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody =
                VerifyGoogleInAppReceiptRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    receipt = receipt
                )
            val response = service.verifyGoogleInAppReceipt(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun verifyGoogleSubReceipt(
        receipt: SubGoogleReceipt,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody =
                VerifyGoogleSubReceiptRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    receipt = receipt
                )
            val response = service.verifyGoogleSubReceipt(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun recoveryGoogleInAppReceipt(
        receipts: List<InAppGoogleReceipt>,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = RecoveryGoogleInAppReceiptRequestBody(
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                receipts = receipts
            )
            val response = service.recoveryGoogleInAppReceipt(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun recoveryGoogleSubReceipt(
        receipts: List<SubGoogleReceipt>,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = RecoveryGoogleSubReceiptRequestBody(
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                receipts = receipts
            )
            val response = service.recoveryGoogleSubReceipt(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun getAuthByCMoney(appId: Int, domain: String, url: String): Result<Boolean> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getAuthByCMoney(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                )
                val responseBody = response.checkResponseBody(gson)
                responseBody.isAuth ?: false
            }
        }

    override suspend fun getHistoryCount(
        productType: Long,
        functionIds: Long,
        domain: String,
        url: String
    ): Result<Long> {
        return withContext(dispatcher.io()) {
            runCatching {
                val response = service.getHistoryCount(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    productType = productType,
                    functionIds = functionIds
                )
                val responseBody = response.checkResponseBody(gson)
                val bodyString = responseBody.string()
                val jsonObject = JsonParser.parseString(bodyString).asJsonObject
                jsonObject.get(functionIds.toString()).asLong
            }
        }
    }
}
