package com.cmoney.backend2.emilystock.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.emilystock.service.api.getemilycommkeys.GetEmilyCommKeysResponse
import com.cmoney.backend2.emilystock.service.api.getfiltercondition.GetFilterConditionResponse
import com.cmoney.backend2.emilystock.service.api.getstockinfos.GetStockInfosResponse
import com.cmoney.backend2.emilystock.service.api.gettargetconstitution.GetTargetConstitution
import com.cmoney.backend2.emilystock.service.api.gettargetstockinfos.GetTargetStockInfos
import com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord.GetTrafficLightRecord
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class EmilyWebImpl(
    private val setting: Setting,
    private val service: EmilyService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : EmilyWeb {
    override suspend fun getEmilyCommKeys(): Result<GetEmilyCommKeysResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getEmilyCommKeys(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun getStockInfos(isTeacherDefault: Boolean): Result<GetStockInfosResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getStockInfos(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    isTeacherDefault = isTeacherDefault,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun getTargetStockInfos(
        isTeacherDefault: Boolean,
        commKeyList: List<String>
    ): Result<GetTargetStockInfos> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getTargetStockInfos(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    isTeacherDefault = isTeacherDefault,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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
        commKey: String
    ): Result<GetTargetConstitution> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getTargetConstitution(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    isTeacherDefault = isTeacherDefault,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    commKey = commKey
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getFilterCondition(): Result<GetFilterConditionResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getFilterCondition(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun getTrafficLightRecord(): Result<GetTrafficLightRecord> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getTrafficLightRecord(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

}
