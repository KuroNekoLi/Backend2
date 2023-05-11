package com.cmoney.backend2.dtno.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toListOfType
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.dtno.service.api.getLatestBasicInfo.BasicInfoResponseBody
import com.cmoney.backend2.dtno.service.api.getklindata.KLineData
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class DtnoWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: DtnoService,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider,
) : DtnoWeb {

    override suspend fun getKLineData(
        commKey: String,
        timeRangeType: Int,
        number: Int,
        domain: String,
        url: String
    ): Result<List<KLineData>> = withContext(dispatcherProvider.io()) {
        runCatching {
            val response = service.getKLineData(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commKey = commKey,
                timeRangeType = timeRangeType,
                number = number,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
                .toListOfType(gson = gson)
        }
    }

    override suspend fun getLatestBasicInfo(
        commKeys: List<String>,
        appServiceId: Int,
        domain: String,
        url: String
    ): Result<BasicInfoResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            val response = service.getLatestBasicInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commKeys = commKeys.toCommKeyList(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
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