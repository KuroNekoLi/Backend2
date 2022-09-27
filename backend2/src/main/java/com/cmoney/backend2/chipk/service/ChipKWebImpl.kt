package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.chipk.service.api.futuredaytradedtnodata.FutureDayTradeDtnoData
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfo
import com.cmoney.backend2.chipk.service.api.internationalkchart.ProductType
import com.cmoney.backend2.chipk.service.api.internationalkchart.TickInfoSet
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext

class ChipKWebImpl(
    private val setting: Setting,
    private val service: ChipKService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : ChipKWeb {

    override suspend fun getData(commKey: String, type: Int): Result<DtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getData(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    stockId = commKey,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    type = type
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }

    override suspend fun getIndexForeignInvestment(tickCount: Int): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexForeignInvestment(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    tickCount = tickCount
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexMain(tickCount: Int): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexMain(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    tickCount = tickCount
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexFunded(tickCount: Int): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexFunded(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    tickCount = tickCount
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getInternationalKChart(
        id: String,
        productType: ProductType
    ): Result<TickInfoSet> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getInternationalKData(
                authorization = setting.accessToken.createAuthorizationBearer(),
                productType = productType.value,
                productKey = id,
                appId = setting.appId
            )
            response.checkResponseBody(gson)
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getCreditRate(): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getCreditRate(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexCalculateRate(commKey: String, timeRange: Int): Result<DtnoData> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexCalculateRate(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    commKey = commKey,
                    timeRange = timeRange,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }
    }

    override suspend fun getIndexKData(commKey: String, timeRange: Int): Result<DtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getIndexKData(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    commKey = commKey,
                    timeRange = timeRange,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkResponseBody(gson).checkIWithError().toRealResponse()
            }
        }

    override suspend fun getChipKData(fundId: Int, params: String): Result<DtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getChipKData(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    fundId = fundId,
                    params = params,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                ).checkResponseBody(gson)
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getOfficialStockPickData(
        index: Int,
        pageType: Int
    ): Result<OfficialStockInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficialStockPickData(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    index = index,
                    type = pageType
                ).checkResponseBody(gson)
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getOfficialStockPickTitle(type: Int): Result<List<String>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getOfficialStockPickTitle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    type = type
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .toJsonArrayWithErrorResponse<List<String>>()
            }
        }

    /**
     * 處理正常回傳是JsonArray 發生錯誤是JsonObject 的api回傳
     *
     * @param T
     * @return
     */
    @Throws(ServerException::class)
    private inline fun <reified T> JsonElement.toJsonArrayWithErrorResponse() : T{
        val responseResult = if(this.isJsonArray){
            try {
                gson.fromJson<T>(this,object : TypeToken<T>() {}.type)
            }catch (exception : JsonSyntaxException){
                null
            }
        }else{
            null
        }

        return if(responseResult != null){
            responseResult
        }else{
            val error = gson.fromJson<CMoneyError>(this,object : TypeToken<CMoneyError>() {}.type)
            throw ServerException(
                error.detail?.code?: Constant.SERVICE_ERROR_CODE,
                error.detail?.message.orEmpty()
            )
        }
    }

    /**
     * 期貨盤後資訊
     * 服務 - 官股、融資
     * 取得盤後官股、融資變動以及三大法人買賣超
     */
    override suspend fun getFutureDayTradeIndexAnalysis(): Result<FutureDayTradeDtnoData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getFutureDayTradeIndexAnalysis(
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

