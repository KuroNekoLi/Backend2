package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.chipk.service.api.futuredaytradedtnodata.FutureDayTradeDtnoWithError
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfoWithError
import com.cmoney.backend2.chipk.service.api.internationalkchart.TickInfoSetWithError
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ChipKService {

    /**
     * 服務6-2. 籌碼K統一要求Dtno的方法
     */
    @RecordApi(cmoneyAction = "getdata")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getData(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "GetData",
        @Field("stockId") stockId: String,
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("type") type: Int
    ): Response<DtnoWithError>

    /**
     * 服務6-6. 要求大盤外資的資料(TWA00)
     */
    @RecordApi(cmoneyAction = "indexforeigninvestment")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexForeignInvestment(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "IndexForeignInvestment",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("tickCount") tickCount: Int
    ): Response<DtnoWithError>

    /**
     * 服務6-7. 要求大盤主力的資料(TWA00)
     */
    @RecordApi(cmoneyAction = "indexmain")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexMain(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "IndexMain",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("tickCount") tickCount: Int
    ): Response<DtnoWithError>

    /**
     * 服務6-8. 要求大盤資券資料(TWA00)
     */
    @RecordApi(cmoneyAction = "indexfunded")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexFunded(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "IndexFunded",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("tickCount") tickCount: Int
    ): Response<DtnoWithError>

    /**
     * 服務6-9. 要求國際盤後資料
     *
     * @param productType 商品類別( 1: 國際指數 2:全球期貨)
     * @return
     */
    @RecordApi(cmoneyAction = "internationalkchart")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getInternationalKData(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "InternationalKChart",
        @Field("ProductType") productType: Int,
        @Field("ProductKey") productKey: String,
        @Field("appId") appId: Int
    ): Response<TickInfoSetWithError>

    /**
     * 服務6-10. 取得加權指數融資維持率
     */
    @RecordApi(cmoneyAction = "getcreditrate")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getCreditRate(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "GetCreditRate",
        @Field("appId") appId: Int,
        @Field("guid") guid: String
    ): Response<DtnoWithError>

    /**
     * 服務6-11. 取得指數技術圖
     */
    @RecordApi(cmoneyAction = "getindexcalculaterate")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexCalculateRate(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "GetIndexCalculateRate",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("CommKey") commKey: String,
        @Field("TimeRange") timeRange: Int
    ): Response<DtnoWithError>

    /**
     * 服務6-12. 取得K圖資料
     */
    @RecordApi(cmoneyAction = "getindexkdata")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexKData(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "GetIndexKData",
        @Field("CommKey") commKey: String,
        @Field("TimeRange") timeRange: Int,
        @Field("appId") appId: Int,
        @Field("guid") guid: String
    ): Response<DtnoWithError>

    /**
     * 服務4-1. 向國鳴用封包要服務(已無使用)
     */
    @RecordApi(cmoneyAction = "getchipkdata")
    @FormUrlEncoded
    @POST("chipk/ashx/ChipK.ashx")
    suspend fun getChipKData(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "GetChipKData",
        @Field("fundId") fundId: Int,
        @Field("params") params: String,
        @Field("guid") guid: String,
        @Field("appId") appId: Int
    ): Response<DtnoWithError>

    /**
     * 取得官方資料
     */
    @RecordApi(cmoneyAction = "getofficialstockpick")
    @FormUrlEncoded
    @POST("chipk/Ashx/GetDtnoData.ashx")
    suspend fun getOfficialStockPickData(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "GetOfficialStockPick",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("index") index: Int,
        @Field("type") type: Int
    ): Response<OfficialStockInfoWithError>

    /**
     * 取得官方標題
     */
    @RecordApi(cmoneyAction = "getofficialstockpicktitle")
    @FormUrlEncoded
    @POST("chipk/Ashx/GetDtnoData.ashx")
    suspend fun getOfficialStockPickTitle(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "GetOfficialStockPickTitle",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("type") type: Int
    ): Response<JsonElement>

    /**
     * 期貨盤後資訊
     * 服務 - 官股、融資
     * 取得盤後官股、融資變動以及三大法人買賣超
     */
    @RecordApi(cmoneyAction = "IndexAnalysis")
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getFutureDayTradeIndexAnalysis(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "IndexAnalysis",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("needLog") needLog: Boolean = true
    ): Response<FutureDayTradeDtnoWithError>
}
