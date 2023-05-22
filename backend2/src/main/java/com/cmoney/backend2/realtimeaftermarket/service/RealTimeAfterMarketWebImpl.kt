package com.cmoney.backend2.realtimeaftermarket.service

import com.cmoney.backend2.base.extension.checkISuccess
import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks.InternationalNewTicks
import com.cmoney.backend2.realtimeaftermarket.service.api.getafterhourstime.AfterHoursTime
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.GetCommListResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail.StockDealDetail
import com.cmoney.backend2.realtimeaftermarket.service.api.getforeignexchangeticks.GetForeignExchangeTickResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getisintradeday.GetIsInTradeDayResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.MarketNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo.NewTickInfo
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.SingleStockNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex.GetStocksInIndexResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.searchstock.ResultEntry
import com.cmoney.backend2.realtimeaftermarket.service.api.searchustock.UsResultEntry
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import kotlinx.coroutines.withContext

class RealTimeAfterMarketWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: RealTimeAfterMarketService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : RealTimeAfterMarketWeb {

    override suspend fun getCommList(
        areaIds: List<String>,
        domain: String,
        url: String
    ): Result<GetCommListResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                areaIds = areaIds.joinToString(","),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getNewTickInfo(
        commKeys: List<String>,
        statusCodes: List<Int>,
        isSimplified: Boolean,
        domain: String,
        url: String
    ): Result<NewTickInfo> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getNewTickInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commKeys = commKeys.joinWithCommand(),
                statusCodes = statusCodes.map { code -> code.toString() }.joinWithCommand(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                isSimplified = isSimplified
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    override suspend fun getSingleStockLongNewTick(
        commKey: String,
        statusCode: String,
        domain: String,
        url: String
    ): Result<SingleStockNewTick> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getSingleStockNewTick(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commKey = commKey,
                statusCode = statusCode,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    /**
     * 服務7-2. Polling取得單股的大盤指數Tick資訊(加上身份識別)
     *
     */
    override suspend fun getMarketNewTick(
        commKey: String,
        statusCode: String,
        domain: String,
        url: String
    ): Result<MarketNewTick> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getMarketNewTick(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commkey = commKey,
                statusCode = statusCode,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )

            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    override suspend fun getInternationalNewTick(
        commKey: String,
        statusCode: String,
        domain: String,
        url: String
    ): Result<InternationalNewTicks> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getInternationalNewTick(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commKey = commKey,
                statusCode = statusCode,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    override suspend fun getDtno(
        dtno: Long,
        paramStr: String,
        assignSpid: String,
        keyMap: String,
        filterNo: Long,
        domain: String,
        url: String
    ): Result<DtnoData> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getDtno(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                dtno = dtno,
                paramStr = paramStr,
                assignSpid = assignSpid,
                keyMap = keyMap,
                filterNo = filterNo
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAfterHoursTime(
        domain: String,
        url: String
    ): Result<AfterHoursTime> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getAfterHoursTime(
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

    override suspend fun searchStock(
        queryKey: String,
        domain: String,
        url: String
    ): Result<List<ResultEntry>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.searchStock(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                queryKey = queryKey
            )
            response.checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun searchUsStock(
        queryKey: String,
        domain: String,
        url: String
    ): Result<List<UsResultEntry>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.searchUsStock(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                queryKey = queryKey
            )
            response.checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getForeignExchangeTicks(
        commKeyWithStatusCodes: List<Pair<String, Int>>,
        domain: String,
        url: String
    ): Result<GetForeignExchangeTickResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val commKeys = commKeyWithStatusCodes.joinToString(",") { (commKey, _) ->
                commKey
            }
            val statusCodes = commKeyWithStatusCodes.joinToString(",") { (_, statusCode) ->
                statusCode.toString()
            }
            service.getForeignExchangeTicks(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                guid = manager.getIdentityToken().getMemberGuid(),
                appId = manager.getAppId(),
                commKeys = commKeys,
                statusCodes = statusCodes
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getStockDealDetail(
        commKey: String,
        perReturnCode: Int,
        timeCode: Int,
        domain: String,
        url: String
    ): Result<StockDealDetail> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getStockDealDetail(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                commKey = commKey,
                perReturnCode = perReturnCode,
                timeCode = timeCode
            )

            response.checkIsSuccessful()
                .requireBody()
                .toRealResponse()
        }
    }

    override suspend fun getIsInTradeDay(
        domain: String,
        url: String
    ): Result<GetIsInTradeDayResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getIsInTradeDay(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getStockSinIndex(
        commKey: String,
        domain: String,
        url: String
    ): Result<GetStocksInIndexResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getStockSinIndex(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                commKey = commKey
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }
    
    private fun List<String>.joinWithCommand(): String {
        return this.joinToString(separator = ",")
    }
}