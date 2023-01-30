package com.cmoney.backend2.billing.service

import com.cmoney.backend2.base.extension.checkISuccess
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
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
import com.cmoney.backend2.billing.service.common.Authorization
import com.cmoney.backend2.billing.service.common.InAppGoogleReceipt
import com.cmoney.backend2.billing.service.common.InAppHuaweiReceipt
import com.cmoney.backend2.billing.service.common.SubGoogleReceipt
import com.cmoney.backend2.billing.service.common.SubHuaweiReceipt
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.withContext

class BillingWebImpl(
    private val service: BillingService,
    private val gson: Gson,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : BillingWeb {

    override suspend fun getDeveloperPayload(): Result<Long> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = GetDeveloperPayloadRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                val response = service.getDeveloperPayload(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = requestBody
                )
                val responseBody = response.checkResponseBody(gson)
                responseBody.id ?: -1
            }
        }

    override suspend fun isReadyToPurchase(): Result<Boolean> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = IsPurchasableRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                val response = service.isReadyToPurchase(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    platform = setting.platform.code,
                    requestBody = requestBody
                )
                val responseBody = response.checkResponseBody(gson)
                responseBody.isPurchasable ?: false
            }
        }

    override suspend fun getProductInfo(): Result<List<ProductInformation>> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = GetProductInfoRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                val response = service.getIapProductInformation(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = requestBody,
                    platform = setting.platform.code
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun getAuthStatus(memberApiParam: MemberApiParam): Result<Authorization> =
        getAuthStatus()

    override suspend fun getAuthStatus(): Result<Authorization> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getAuthStatus(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
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

    override suspend fun getTargetAppAuthStatus(queryAppId: Int): Result<Authorization> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getTargetAppAuthStatus(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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
        receipt: InAppHuaweiReceipt
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = VerifyHuaweiInAppReceiptRequestBody(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                receipt = receipt
            )
            val response = service.verifyHuaweiInAppReceipt(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun verifyHuaweiSubReceipt(receipt: SubHuaweiReceipt): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = VerifyHuaweiSubReceiptRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    receipt = receipt
                )
                val response = service.verifyHuaweiSubReceipt(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = requestBody
                )
                response.handleNoContent(gson)
            }
        }

    override suspend fun recoveryHuaweiInAppReceipt(
        receipts: List<InAppHuaweiReceipt>
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = RecoveryHuaweiInAppReceiptRequestBody(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                receipts = receipts
            )
            val response = service.recoveryHuaweiInAppReceipt(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            )
            return@runCatching response.handleNoContent(gson)
        }
    }

    override suspend fun recoveryHuaweiSubReceipt(receipts: List<SubHuaweiReceipt>): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val requestBody = RecoveryHuaweiSubReceiptRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    receipts = receipts
                )
                val response = service.recoveryHuaweiSubReceipt(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = requestBody
                )
                response.handleNoContent(gson)
            }
        }

    override suspend fun verifyGoogleInAppReceipt(
        receipt: InAppGoogleReceipt
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody =
                VerifyGoogleInAppReceiptRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    receipt = receipt
                )
            val response = service.verifyGoogleInAppReceipt(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun verifyGoogleSubReceipt(
        receipt: SubGoogleReceipt
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody =
                VerifyGoogleSubReceiptRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    receipt = receipt
                )
            val response = service.verifyGoogleSubReceipt(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun recoveryGoogleInAppReceipt(
        receipts: List<InAppGoogleReceipt>
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = RecoveryGoogleInAppReceiptRequestBody(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                receipts = receipts
            )
            val response = service.recoveryGoogleInAppReceipt(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun recoveryGoogleSubReceipt(
        receipts: List<SubGoogleReceipt>
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = RecoveryGoogleSubReceiptRequestBody(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                receipts = receipts
            )
            val response = service.recoveryGoogleSubReceipt(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun getAuthByCMoney(appId: Int): Result<Boolean> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getAuthByCMoney(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = appId
                )
                val responseBody = response.checkResponseBody(gson)
                responseBody.isAuth ?: false
            }
        }

    override suspend fun getHistoryCount(productType: Long, functionIds: Long): Result<Long> {
        return withContext(dispatcher.io()) {
            runCatching {
                val response = service.getHistoryCount(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    productType = productType,
                    functionIds = functionIds
                )
                val responseBody = response.checkResponseBody(gson)
                val bodyString=responseBody.string()
                val jsonObject = JsonParser.parseString(bodyString).asJsonObject
                jsonObject.get(functionIds.toString()).asLong
            }
        }
    }
}
