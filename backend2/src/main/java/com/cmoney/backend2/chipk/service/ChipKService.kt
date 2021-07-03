package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfoWithError
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
    @RecordApi
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
     * 服務6-12. 取得K圖資料
     *
     */
    @RecordApi
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

    @RecordApi
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
    @RecordApi
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
    @RecordApi
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
