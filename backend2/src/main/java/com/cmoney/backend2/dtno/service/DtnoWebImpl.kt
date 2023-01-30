package com.cmoney.backend2.dtno.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toListOfSomething
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.dtno.service.api.getLatestBasicInfo.BasicInfoResponseBody
import com.cmoney.backend2.dtno.service.api.getklindata.KLineData
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class DtnoWebImpl(
    private val gson: Gson,
    private val service: DtnoService,
    private val setting: Setting,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : DtnoWeb {

    override suspend fun getKLineData(
        apiParam: MemberApiParam,
        commKey: String,
        timeRangeType: Int,
        number: Int
    ): Result<List<KLineData>> = getKLineData(commKey, timeRangeType, number)

    override suspend fun getKLineData(
        commKey: String,
        timeRangeType: Int,
        number: Int
    ): Result<List<KLineData>> = withContext(dispatcherProvider.io()) {
        runCatching {
            val response = service.getKLineData(
                authorization = setting.accessToken.createAuthorizationBearer(),
                commKey = commKey,
                timeRangeType = timeRangeType,
                number = number,
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
                .toListOfSomething<KLineData>(gson)
        }
    }

    override suspend fun getLatestBasicInfo(
        apiParam: MemberApiParam,
        commKeys: List<String>,
        appServiceId: Int
    ): Result<BasicInfoResponseBody> = getLatestBasicInfo(commKeys, appServiceId)

    override suspend fun getLatestBasicInfo(
        commKeys: List<String>,
        appServiceId: Int
    ): Result<BasicInfoResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            val response = service.getLatestBasicInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                commKeys = commKeys.toCommKeyList(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                appServiceId = appServiceId
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 以逗號間隔
     */
    private fun List<String>.toCommKeyList(): String {
        return this.joinToString(separator = ",")
    }
}