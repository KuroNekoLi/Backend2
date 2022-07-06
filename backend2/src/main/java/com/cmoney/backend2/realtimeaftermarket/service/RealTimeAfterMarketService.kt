package com.cmoney.backend2.realtimeaftermarket.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks.InternationalNewTicks
import com.cmoney.backend2.realtimeaftermarket.service.api.getafterhourstime.AfterHoursTimeWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.GetCommListResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail.StockDealDetailWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getforeignexchangeticks.GetForeignExchangeTickResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getisintradeday.GetIsInTradeDayResponseBodyWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.MarketNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo.NewTickInfo
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.SingleStockNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex.GetStockSinIndexResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.searchstock.ResultEntry
import com.cmoney.backend2.realtimeaftermarket.service.api.searchustock.UsResultEntry
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RealTimeAfterMarketService {

    /**
     * 服務4-2.取得股市商品清單
     *
     * @param authorization 授權Token
     * @param action API操作
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
     * 上述已逗號分開
     * @param appId App編號
     * @param guid 會員的guid
     * @return
     */
    @RecordApi(cmoneyAction = "getcommlist")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getCommList(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getcommlist",
        @Field("AreaIds") areaIds: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetCommListResponseBody>

    /**
     * 服務5-2 Polling取得多股的即時Tick資訊 (包含國際&午後&台股)
     *
     * @param commKeys 監控複數的股票編號(逗號分隔)
     * @param statusCodes 複數股票的polling狀態碼(逗號分隔)
     * @param appId App編號
     * @param guid 該會員的guid
     * @param isSimplified 是否要簡化版(預設為false)
     *
     */
    @RecordApi(cmoneyAction = "getnewtickinfo")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getNewTickInfo(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getnewtickinfo",

        /**
         * 逗號分隔
         */
        @Field("commkeys") commKeys: String,

        /**
         * 逗號分隔
         */
        @Field("statusCodes") statusCodes: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        /**
         * 是否簡化，預設False
         */
        @Field("isSimplified") isSimplified: Boolean = false
    ): Response<NewTickInfo>

    /**
     * 服務6-2. Polling單檔股票即時Tick資訊
     *
     * @param commKey 監控股票的編號
     * @param statusCode 該股票的polling狀態碼
     * @param appId App編號
     * @param guid 該會員的guid
     *
     */
    @RecordApi(cmoneyAction = "getstockinstantdata")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getSingleStockNewTick(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getstockinstantdata",
        @Field("commkey") commKey: String,
        @Field("statusCodes") statusCode: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<SingleStockNewTick>

    /**
     * 服務7-2. Polling取得單股的大盤指數Tick資訊(加上身份識別)
     *
     * @param commkey 監控的股票編號
     * @param statusCode 股票的polling狀態碼
     * @param appId App編號
     * @param guid 該會員的guid
     *
     */
    @RecordApi(cmoneyAction = "getindextickinfo")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getMarketNewTick(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getindextickinfo",
        @Field("Commkeys") commkey: String,
        @Field("StatusCodes") statusCode: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<MarketNewTick>

    /**
     * 服務8-2. Polling取得國際指數Tick資訊(加上身份識別)
     *
     * @param commKey 監控的股票編號
     * @param statusCode 股票的polling狀態碼
     * @param appId App編號
     * @param guid 該會員的guid
     *
     */
    @RecordApi(cmoneyAction = "getinternationalticks")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InternationalTrading.ashx")
    suspend fun getInternationalNewTick(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getinternationalticks",
        @Field("Commkey") commKey: String,
        @Field("StatusCode") statusCode: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<InternationalNewTicks>

    /**
     * 服務9-2. 取得DtNO資料
     *
     * @param dtno 表格編號
     * @param paramStr 參數字串
     * @param assignSpid 參數
     * @param keyMap 參數
     * @param filterNo 參數
     * @param appId App編號
     * @param guid 該會員的guid
     *
     */
    @RecordApi(cmoneyAction = "getdtnodata")
    @FormUrlEncoded
    @POST("MobileService/ashx/GetDtnoData.ashx")
    suspend fun getDtno(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getdtnodata",
        @Field("DtNo") dtno: Long,
        @Field("ParamStr") paramStr: String,
        @Field("AssignSPID") assignSpid: String,
        @Field("KeyMap") keyMap: String,
        @Field("FilterNo") filterNo: Long,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<DtnoWithError>

    /**
     * 服務10. 取得盤後資料日期(加上身份識別)
     */
    @RecordApi(cmoneyAction = "getafterhourstime")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getAfterHoursTime(
            @Header("Authorization") authorization: String,
            @Field("action") action: String = "getafterhourstime",
            @Field("AppId") appId: Int,
            @Field("Guid") guid: String
    ): Response<AfterHoursTimeWithError>

    /**
     * 服務11. 取得搜尋結果(台股)
     *
     * @param queryKey 關鍵字
     *
     */
    @RecordApi(cmoneyAction = "searchstock")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun searchStock(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "searchstock",
        @Field("Querykey") queryKey: String
    ): Response<List<ResultEntry>>

    /**
     * 服務11-2. 取得搜尋結果(美股)
     *
     * @param queryKey 關鍵字
     *
     */
    @RecordApi(cmoneyAction = "searchustock")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun searchUsStock(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "searchustock",
        @Field("Querykey") queryKey: String
    ): Response<List<UsResultEntry>>

    /**
     * 服務13. 取得外匯即時
     *
     * @param authorization 授權Token
     * @param action API操作
     * @param appId App編號
     * @param guid 會員的guid
     * @param commKeys 幣別代號(以逗號分隔)
     * @param statusCodes 狀態碼(以逗號分隔)
     */
    @RecordApi(cmoneyAction = "getforeignexchangeticks")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/ForeignExchangeTrading.ashx")
    suspend fun getForeignExchangeTicks(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getforeignexchangeticks",
        @Field("guid") guid: String,
        @Field("appId") appId: Int,
        @Field("commkeys") commKeys: String,
        @Field("statusCodes") statusCodes: String
    ): Response<GetForeignExchangeTickResponseBody>

    /**
     * 服務19. 取得台股成交明細
     *
     * @param commKey 股票代號
     * @param guid 該會員的Guid
     * @param appId App編號
     * @param timeCode 時序號碼 (依時序號碼往前推N筆，等於0會回覆最新N筆)
     * @param perReturnCode 每批次回覆成交明細數量
     *
     */
    @RecordApi(cmoneyAction = "getdealdetail")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getStockDealDetail(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getdealdetail",
        @Field("Commkey") commKey: String,
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("TimeCode") timeCode: Int,
        @Field("PerReturnCount") perReturnCode: Int
    ): Response<StockDealDetailWithError>

    /**
     * 服務20. 取得是否盤中
     */
    @RecordApi(cmoneyAction = "getisintraday")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getIsInTradeDay(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getisintraday",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int
    ): Response<GetIsInTradeDayResponseBodyWithError>
    /**
     * 服務23.取得指數的成分股票清單
     * @param commKey 類股指數代號
     * @param appId App編號
     * @param guid 該會員的guid
     */
    @RecordApi(cmoneyAction = "getstocksinindex")
    @FormUrlEncoded
    @POST("MobileService/ashx/InstantTrading/InstantTrading.ashx")
    suspend fun getStockSinIndex(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getstocksinindex",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("Commkey") commKey: String,
    ): Response<GetStockSinIndexResponseBody>
}