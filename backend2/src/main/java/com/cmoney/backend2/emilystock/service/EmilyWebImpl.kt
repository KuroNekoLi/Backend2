package com.cmoney.backend2.emilystock.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.emilystock.service.api.getemilycommkeys.GetEmilyCommKeysResponse
import com.cmoney.backend2.emilystock.service.api.getfiltercondition.GetFilterConditionResponse
import com.cmoney.backend2.emilystock.service.api.getstockinfos.GetStockInfosResponse
import com.cmoney.backend2.emilystock.service.api.gettargetconstitution.GetTargetConstitution
import com.cmoney.backend2.emilystock.service.api.gettargetstockinfos.GetTargetStockInfos
import com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord.GetTrafficLightRecord
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class EmilyWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: EmilyService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : EmilyWeb {
    override suspend fun getEmilyCommKeys(
        domain: String,
        url: String
    ): Result<GetEmilyCommKeysResponse> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getEmilyCommKeys(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getStockInfos(
        isTeacherDefault: Boolean,
        domain: String,
        url: String
    ): Result<GetStockInfosResponse> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getStockInfos(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                isTeacherDefault = isTeacherDefault,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getTargetStockInfos(
        isTeacherDefault: Boolean,
        commKeyList: List<String>,
        domain: String,
        url: String
    ): Result<GetTargetStockInfos> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getTargetStockInfos(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                isTeacherDefault = isTeacherDefault,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                commKeys = commKeyList
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getTargetConstitution(
        isTeacherDefault: Boolean,
        commKey: String,
        domain: String,
        url: String
    ): Result<GetTargetConstitution> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getTargetConstitution(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                isTeacherDefault = isTeacherDefault,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                commKey = commKey
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getFilterCondition(
        domain: String,
        url: String
    ): Result<GetFilterConditionResponse> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getFilterCondition(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getTrafficLightRecord(
        domain: String,
        url: String
    ): Result<GetTrafficLightRecord> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getTrafficLightRecord(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

}
