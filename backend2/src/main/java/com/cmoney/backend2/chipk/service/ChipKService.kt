package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfoWithError
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ChipKService {

    // 服務6-2. 籌碼K統一要求Dtno的方法
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

    //服務6-6. 要求大盤外資的資料(TWA00)
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexForeignInvestment(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "IndexForeignInvestment",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("tickCount") tickCount: Int
    ): Response<DtnoWithError>

    //服務6-7. 要求大盤主力的資料(TWA00)
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexMain(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "IndexMain",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("tickCount") tickCount: Int
    ): Response<DtnoWithError>

    //服務6-8. 要求大盤資券資料(TWA00)
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getIndexFunded(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "IndexFunded",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("tickCount") tickCount: Int
    ): Response<DtnoWithError>

    //服務6-10. 取得加權指數融資維持率
    @FormUrlEncoded
    @POST("chipk/ashx/GetDtnoData.ashx")
    suspend fun getCreditRate(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "GetCreditRate",
        @Field("appId") appId: Int,
        @Field("guid") guid: String
    ): Response<DtnoWithError>

    //服務6-11. 取得指數技術圖
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

    // 服務6-12. 取得K圖資料
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
    @FormUrlEncoded
    @POST("chipk/Ashx/GetDtnoData.ashx")
    suspend fun getOfficialStockPickTitle(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "GetOfficialStockPickTitle",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("type") type: Int
    ): Response<JsonElement>
}
