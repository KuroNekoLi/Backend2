package com.cmoney.backend2.emilystock.service

import com.cmoney.backend2.emilystock.service.api.getemilycommkeys.GetEmilyCommKeysResponse
import com.cmoney.backend2.emilystock.service.api.getfiltercondition.GetFilterConditionResponse
import com.cmoney.backend2.emilystock.service.api.getstockinfos.GetStockInfosResponse
import com.cmoney.backend2.emilystock.service.api.gettargetconstitution.GetTargetConstitutionWithError
import com.cmoney.backend2.emilystock.service.api.gettargetstockinfos.GetTargetStockInfosWithError
import com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord.GetTrafficLightRecordWithError
import retrofit2.Response
import retrofit2.http.*

interface EmilyService {

    /**
     * 取得艾蜜莉股票清單
     */
    @FormUrlEncoded
    @POST("EmilyFixedStock/api/EmilyStock/GetEmilyCommKeys")
    suspend fun getEmilyCommKeys(
        @Header("Authorization") authorization: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetEmilyCommKeysResponse>

    /**
     * 取得艾蜜莉股票清單詳細資訊
     */
    @FormUrlEncoded
    @POST("EmilyFixedStock/api/EmilyStock/GetStockInfos")
    suspend fun getStockInfos(
        @Header("Authorization") authorization: String,
        @Query("teacherDefault") isTeacherDefault: Boolean,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetStockInfosResponse>

    /**
     * 取得指定股票艾蜜莉股票資訊
     */
    @FormUrlEncoded
    @POST("EmilyFixedStock/api/EmilyStock/GetTargetStockInfos")
    suspend fun getTargetStockInfos(
        @Header("Authorization") authorization: String,
        @Query("teacherDefault") isTeacherDefault: Boolean,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("CommKeys") commKeys: List<String>
    ): Response<GetTargetStockInfosWithError>

    /**
     * 取得指定股票的體質評估
     */
    @FormUrlEncoded
    @POST("EmilyFixedStock/api/EmilyStock/GetTargetConstitution")
    suspend fun getTargetConstitution(
        @Header("Authorization") authorization: String,
        @Query("teacherDefault") isTeacherDefault: Boolean,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("CommKey") commKey: String
    ): Response<GetTargetConstitutionWithError>

    /**
     * 取得篩選條件
     */
    @FormUrlEncoded
    @POST("EmilyFixedStock/api/EmilyStock/GetFilterCondition")
    suspend fun getFilterCondition(
        @Header("Authorization") authorization: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetFilterConditionResponse>

    /**
     * 取得某會員的紅綠燈紀錄(需要再測試)
     */
    @FormUrlEncoded
    @POST("EmilyFixedStock/api/EmilyStock/GetTrafficLightRecord")
    suspend fun getTrafficLightRecord(
        @Header("Authorization") authorization: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetTrafficLightRecordWithError>
}
