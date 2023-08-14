package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toJsonArrayWithErrorResponse
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.chipk.service.api.futuredaytradedtnodata.FutureDayTradeDtnoData
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfo
import com.cmoney.backend2.chipk.service.api.internationalkchart.ProductType
import com.cmoney.backend2.chipk.service.api.internationalkchart.TickInfoSet
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class ChipKWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: ChipKService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ChipKWeb {

    override suspend fun getData(
        commKey: String,
        type: Int,
        domain: String,
        url: String
    ): Result<DtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getData(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    stockId = commKey,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    type = type
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }

    override suspend fun getIndexForeignInvestment(
        tickCount: Int,
        domain: String,
        url: String
    ): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexForeignInvestment(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    tickCount = tickCount
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexMain(
        tickCount: Int,
        domain: String,
        url: String
    ): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexMain(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    tickCount = tickCount
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexFunded(
        tickCount: Int,
        domain: String,
        url: String
    ): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexFunded(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    tickCount = tickCount
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getInternationalKChart(
        id: String,
        productType: ProductType,
        domain: String,
        url: String
    ): Result<TickInfoSet> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getInternationalKData(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                productType = productType.value,
                productKey = id,
                appId = manager.getAppId()
            )
            response.checkResponseBody(gson)
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getCreditRate(
        domain: String,
        url: String
    ): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getCreditRate(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexCalculateRate(
        commKey: String,
        timeRange: Int,
        domain: String,
        url: String
    ): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexCalculateRate(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    commKey = commKey,
                    timeRange = timeRange,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexKData(
        commKey: String,
        timeRange: Int,
        domain: String,
        url: String
    ): Result<DtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexKData(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    commKey = commKey,
                    timeRange = timeRange,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }

    override suspend fun getChipKData(
        fundId: Int,
        params: String,
        domain: String,
        url: String
    ): Result<DtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getChipKData(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    fundId = fundId,
                    params = params,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                ).checkResponseBody(gson)
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getOfficialStockPickData(
        index: Int,
        pageType: Int,
        domain: String,
        url: String
    ): Result<OfficialStockInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficialStockPickData(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    index = index,
                    type = pageType
                ).checkResponseBody(gson)
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getOfficialStockPickTitle(
        type: Int,
        domain: String,
        url: String
    ): Result<List<String>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getOfficialStockPickTitle(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    type = type
                )
                response.toJsonArrayWithErrorResponse<List<String>>(gson = gson)
            }
        }

    /**
     * 期貨盤後資訊
     * 服務 - 官股、融資
     * 取得盤後官股、融資變動以及三大法人買賣超
     */
    override suspend fun getFutureDayTradeIndexAnalysis(
        domain: String,
        url: String
    ): Result<FutureDayTradeDtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getFutureDayTradeIndexAnalysis(
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

