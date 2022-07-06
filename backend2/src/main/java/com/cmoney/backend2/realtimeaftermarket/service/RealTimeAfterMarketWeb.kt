package com.cmoney.backend2.realtimeaftermarket.service

import com.cmoney.backend2.base.model.request.ApiParam
import com.cmoney.backend2.base.model.request.MemberApiParam
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
import com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex.GetStockSinIndexResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.searchstock.ResultEntry
import com.cmoney.backend2.realtimeaftermarket.service.api.searchustock.UsResultEntry
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Header

interface RealTimeAfterMarketWeb {

    /**
     * 服務4-2.取得股市商品清單
     *
     * @param areaIds 資料範圍
     * 1-籌碼K全球,
     * 2-籌碼K亞洲,
     * 3-籌碼K歐美,
     * 4-籌碼K台灣,
     * 5-籌碼K美股科技,
     * 6-籌碼K美股非科技,
     * 7-籌碼K台股ADR,
     * 8-籌碼K外匯,
     * 9-台股上市類股,
     * 10-台股上櫃類股,
     * 11-台股上市(含個股、指數、TDR、ETF),
     * 12-台股上櫃(含個股、指數、TDR、ETF),
     * 13-台股上市指數彙編類股,
     * 14-台股上櫃指數彙編類股,
     * 15-台股概念股,
     * 16-原力美股)
     * 上述已逗號分開，舉例: 1,2,3
     */
    suspend fun getCommList(areaIds: List<String>): Result<GetCommListResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getNewTickInfo(
        apiParam: MemberApiParam,
        commKeys: List<String>,
        statusCodes: List<Int>,
        isSimplified: Boolean = false
    ): Result<NewTickInfo>

    /**
     * 服務5-2 Polling取得多股的即時Tick資訊 (包含國際&午後&台股)
     */
    suspend fun getNewTickInfo(
        commKeys: List<String>,
        statusCodes: List<Int>,
        isSimplified: Boolean = false
    ): Result<NewTickInfo>

    @Deprecated("ApiParam no longer required")
    suspend fun getSingleStockLongNewTick(
        apiParam: MemberApiParam,
        commKey: String,
        statusCode: String
    ): Result<SingleStockNewTick>

    /**
     * 服務6-2. Polling單檔股票即時Tick資訊
     */
    suspend fun getSingleStockLongNewTick(
        commKey: String,
        statusCode: String
    ): Result<SingleStockNewTick>

    @Deprecated("ApiParam no longer required")
    suspend fun getMarketNewTick(
        apiParam: MemberApiParam,
        commKey: String,
        statusCode: String
    ): Result<MarketNewTick>

    /**
     * 服務7-2. Polling取得單股的大盤指數Tick資訊(加上身份識別)
     */
    suspend fun getMarketNewTick(
        commKey: String,
        statusCode: String
    ): Result<MarketNewTick>

    /**
     * 服務8-2. Polling取得國際指數Tick資訊(加上身份識別)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getInternationalNewTick(
        apiParam: MemberApiParam,
        commKey: String,
        statusCode: String
    ): Result<InternationalNewTicks>

    /**
     * 服務8-2. Polling取得國際指數Tick資訊(加上身份識別)
     */
    suspend fun getInternationalNewTick(
        commKey: String,
        statusCode: String
    ): Result<InternationalNewTicks>

    @Deprecated("ApiParam no longer required")
    suspend fun getDtno(
        apiParam: ApiParam,
        dtno: Long,
        paramStr: String,
        assignSpid: String,
        keyMap: String,
        filterNo: Long
    ): Result<DtnoData>

    /**
     * 取得Dtno報表
     * 外部可以使用 `DtnoData.toListOfSomething` parse成需要的資料格式
     */
    suspend fun getDtno(
        dtno: Long,
        paramStr: String,
        assignSpid: String,
        keyMap: String,
        filterNo: Long
    ): Result<DtnoData>

    suspend fun getAfterHoursTime(): Result<AfterHoursTime>

    /**
     * 服務11. 取得搜尋結果(台股)
     *
     * @param queryKey 股票關鍵字
     */
    suspend fun searchStock(queryKey: String): Result<List<ResultEntry>>

    /**
     * 服務11-2. 取得搜尋結果(美股)
     *
     * @param queryKey 股票關鍵字
     */
    suspend fun searchUsStock(queryKey: String): Result<List<UsResultEntry>>

    /**
     * 服務13. 取得外匯即時
     *
     * @return
     */
    suspend fun getForeignExchangeTicks(
        commKeyWithStatusCodes: List<Pair<String, Int>>
    ): Result<GetForeignExchangeTickResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getStockDealDetail(
        apiParam: MemberApiParam,
        commKey: String,
        perReturnCode: Int,
        timeCode: Int
    ): Result<StockDealDetail>

    /**
     * 服務19 取得台股成交明細
     *
     */
    suspend fun getStockDealDetail(
        commKey: String,
        perReturnCode: Int,
        timeCode: Int
    ): Result<StockDealDetail>

    @Deprecated("ApiParam no longer required")
    suspend fun getIsInTradeDay(
        apiParam: MemberApiParam
    ): Result<GetIsInTradeDayResponseBody>

    /**
     * 服務20. 取得是否盤中
     */
    suspend fun getIsInTradeDay(): Result<GetIsInTradeDayResponseBody>

    /**
     * 服務23.取得指數的成分股票清單
     */
    suspend fun getStockSinIndex(commKey: String): Result<GetStockSinIndexResponseBody>
}