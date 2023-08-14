package com.cmoney.backend2.realtimeaftermarket.service

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

interface RealTimeAfterMarketWeb {

    val manager: GlobalBackend2Manager

    /**
     * 服務4-2.取得股市商品清單
     *
     * @param areaIds 資料範圍
     *
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
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getCommList(
        areaIds: List<String>,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<GetCommListResponseBody>

    /**
     * 服務5-2 Polling取得多股的即時Tick資訊 (包含國際&午後&台股)
     *
     * @param commKeys 商品代碼
     * @param statusCodes 狀態代碼
     * @param isSimplified 是否要簡化版(預設為false)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getNewTickInfo(
        commKeys: List<String>,
        statusCodes: List<Int>,
        isSimplified: Boolean = false,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<NewTickInfo>

    /**
     * 服務6-2. Polling單檔股票即時Tick資訊
     *
     * @param commKey 商品代碼
     * @param statusCode 狀態代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSingleStockLongNewTick(
        commKey: String,
        statusCode: String,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<SingleStockNewTick>

    /**
     * 服務7-2. Polling取得單股的大盤指數Tick資訊(加上身份識別)
     *
     * @param commKey 商品代碼
     * @param statusCode 狀態代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMarketNewTick(
        commKey: String,
        statusCode: String,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<MarketNewTick>

    /**
     * 服務8-2. Polling取得國際指數Tick資訊(加上身份識別)
     *
     * @param commKey 商品代碼
     * @param statusCode 狀態代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getInternationalNewTick(
        commKey: String,
        statusCode: String,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InternationalTrading.ashx"
    ): Result<InternationalNewTicks>

    /**
     * 取得Dtno報表
     * 外部可以使用 `DtnoData.toListOfSomething` parse成需要的資料格式
     *
     * @param dtno 表格編號
     * @param paramStr 參數字串
     * @param assignSpid 參數
     * @param keyMap 參數
     * @param filterNo 參數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getDtno(
        dtno: Long,
        paramStr: String,
        assignSpid: String,
        keyMap: String,
        filterNo: Long,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/GetDtnoData.ashx"
    ): Result<DtnoData>

    suspend fun getAfterHoursTime(
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<AfterHoursTime>

    /**
     * 服務11. 取得搜尋結果(台股)
     *
     * @param queryKey 股票關鍵字
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchStock(
        queryKey: String,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<List<ResultEntry>>

    /**
     * 服務11-2. 取得搜尋結果(美股)
     *
     * @param queryKey 股票關鍵字
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchUsStock(
        queryKey: String,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<List<UsResultEntry>>

    /**
     * 服務13. 取得外匯即時
     *
     * @param commKeyWithStatusCodes 商品代碼與狀態代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getForeignExchangeTicks(
        commKeyWithStatusCodes: List<Pair<String, Int>>,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/ForeignExchangeTrading.ashx"
    ): Result<GetForeignExchangeTickResponseBody>

    /**
     * 服務19 取得台股成交明細
     *
     * @param commKey 商品代碼
     * @param timeCode 時序號碼 (依時序號碼往前推N筆，等於0會回覆最新N筆)
     * @param perReturnCode 每批次回覆成交明細數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockDealDetail(
        commKey: String,
        perReturnCode: Int,
        timeCode: Int,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<StockDealDetail>

    /**
     * 服務20. 取得是否盤中
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getIsInTradeDay(
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<GetIsInTradeDayResponseBody>

    /**
     * 服務23.取得指數的成分股票清單
     *
     * @param commKey 商品代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockSinIndex(
        commKey: String,
        domain: String = manager.getRealtimeAfterMarketSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/InstantTrading/InstantTrading.ashx"
    ): Result<GetStocksInIndexResponseBody>
}