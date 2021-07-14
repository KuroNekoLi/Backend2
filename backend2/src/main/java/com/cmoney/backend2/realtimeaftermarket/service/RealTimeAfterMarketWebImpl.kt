package com.cmoney.backend2.realtimeaftermarket.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.request.ApiParam
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks.InternationalNewTicks
import com.cmoney.backend2.realtimeaftermarket.service.api.getafterhourstime.AfterHoursTime
import com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail.StockDealDetail
import com.cmoney.backend2.realtimeaftermarket.service.api.getforeignexchangeticks.GetForeignExchangeTickResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getisintradeday.GetIsInTradeDayResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.MarketNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo.NewTickInfo
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.SingleStockNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.searchstock.ResultEntry
import kotlinx.coroutines.withContext

class RealTimeAfterMarketWebImpl(
    private val service: RealTimeAfterMarketService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : RealTimeAfterMarketWeb {

    override suspend fun getCommList(areaIds: List<String>) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                areaIds = areaIds.joinToString(","),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    /**
     * 服務5-2 Polling取得多股的即時Tick資訊 (包含國際&午後&台股)
     *
     */
    override suspend fun getNewTickInfo(
        apiParam: MemberApiParam,
        commKeys: List<String>,
        statusCodes: List<Int>,
        isSimplified: Boolean
    ): Result<NewTickInfo> = getNewTickInfo(commKeys, statusCodes, isSimplified)

    /**
     * 服務5-2 Polling取得多股的即時Tick資訊 (包含國際&午後&台股)
     */
    override suspend fun getNewTickInfo(
        commKeys: List<String>,
        statusCodes: List<Int>,
        isSimplified: Boolean
    ): Result<NewTickInfo> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getNewTickInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                commKeys = commKeys.joinWithCommand(),
                statusCodes = statusCodes.map { code -> code.toString() }.joinWithCommand(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                isSimplified = isSimplified
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    /**
     * 服務6-2. Polling單檔股票即時Tick資訊
     */
    override suspend fun getSingleStockLongNewTick(
        apiParam: MemberApiParam,
        commKey: String,
        statusCode: String
    ): Result<SingleStockNewTick> = getSingleStockLongNewTick(commKey, statusCode)

    /**
     * 服務6-2. Polling單檔股票即時Tick資訊
     *
     */
    override suspend fun getSingleStockLongNewTick(
        commKey: String,
        statusCode: String
    ): Result<SingleStockNewTick> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getSingleStockNewTick(
                authorization = setting.accessToken.createAuthorizationBearer(),
                commKey = commKey,
                statusCode = statusCode,
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    /**
     * 服務7-2. Polling取得單股的大盤指數Tick資訊(加上身份識別)
     */
    override suspend fun getMarketNewTick(
        apiParam: MemberApiParam,
        commKey: String,
        statusCode: String
    ): Result<MarketNewTick> = getMarketNewTick(commKey, statusCode)

    /**
     * 服務7-2. Polling取得單股的大盤指數Tick資訊(加上身份識別)
     *
     */
    override suspend fun getMarketNewTick(
        commKey: String,
        statusCode: String
    ): Result<MarketNewTick> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getMarketNewTick(
                authorization = setting.accessToken.createAuthorizationBearer(),
                commkey = commKey,
                statusCode = statusCode,
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )

            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    /**
     * 服務8-2. Polling取得國際指數Tick資訊(加上身份識別)
     *
     */
    override suspend fun getInternationalNewTick(
        apiParam: MemberApiParam,
        commKey: String,
        statusCode: String
    ): Result<InternationalNewTicks> = getInternationalNewTick(commKey, statusCode)

    override suspend fun getInternationalNewTick(
        commKey: String,
        statusCode: String
    ): Result<InternationalNewTicks> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getInternationalNewTick(
                authorization = setting.accessToken.createAuthorizationBearer(),
                commKey = commKey,
                statusCode = statusCode,
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    /**
     * 取得Dtno報表
     * 外部可以使用 `DtnoData.toListOfSomething` parse成需要的資料格式
     */
    override suspend fun getDtno(
        apiParam: ApiParam,
        dtno: Long,
        paramStr: String,
        assignSpid: String,
        keyMap: String,
        filterNo: Long
    ): Result<DtnoData> = getDtno(dtno, paramStr, assignSpid, keyMap, filterNo)

    override suspend fun getDtno(
        dtno: Long,
        paramStr: String,
        assignSpid: String,
        keyMap: String,
        filterNo: Long
    ): Result<DtnoData> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getDtno(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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

    override suspend fun getAfterHoursTime(): Result<AfterHoursTime> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getAfterHoursTime(
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

    /**
     * 服務11. 取得搜尋結果(台股)
     */
    override suspend fun searchStock(queryKey: String): Result<List<ResultEntry>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.searchStock(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    queryKey = queryKey
                )
                response.checkIsSuccessful()
                    .requireBody()
            }
        }

    /**
     * 服務11-2. 取得搜尋結果(美股)
     *
     */
    override suspend fun searchUsStock(queryKey: String) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.searchUsStock(
                authorization = setting.accessToken.createAuthorizationBearer(),
                queryKey = queryKey
            )
            response.checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getForeignExchangeTicks(commKeyWithStatusCodes: List<Pair<String, Int>>): Result<GetForeignExchangeTickResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val commKeys = commKeyWithStatusCodes.joinToString(",") { (commKey, _) ->
                    commKey
                }
                val statusCodes = commKeyWithStatusCodes.joinToString(",") { (_, statusCode) ->
                    statusCode.toString()
                }
                service.getForeignExchangeTicks(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId,
                    commKeys = commKeys,
                    statusCodes = statusCodes
                )
                    .checkIsSuccessful()
                    .requireBody()
            }
        }

    /**
     * 服務19 取得台股成交明細
     */
    override suspend fun getStockDealDetail(
        apiParam: MemberApiParam,
        commKey: String,
        perReturnCode: Int,
        timeCode: Int
    ): Result<StockDealDetail> = getStockDealDetail(commKey, perReturnCode, timeCode)

    override suspend fun getStockDealDetail(
        commKey: String,
        perReturnCode: Int,
        timeCode: Int
    ): Result<StockDealDetail> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getStockDealDetail(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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
        apiParam: MemberApiParam
    ): Result<GetIsInTradeDayResponseBody> = getIsInTradeDay()

    /**
     * 服務20. 取得是否盤中
     */
    override suspend fun getIsInTradeDay() = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getIsInTradeDay(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
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