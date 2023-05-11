package com.cmoney.backend2.dtno.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.dtno.service.api.getLatestBasicInfo.BasicInfoResponseBodyWithError
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface DtnoService {

    /**
     * 取得k線資料
     */
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getKLineData(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Commkey") commKey: String,
        @Field("TimeRangeType") timeRangeType: Int,
        @Field("Number") number: Int,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<DtnoWithError>

    /**
     * 取得最新基本資訊
     */
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getLatestBasicInfo(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Commkeys") commKeys: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("AppServiceId") appServiceId: Int
    ): Response<BasicInfoResponseBodyWithError>
}