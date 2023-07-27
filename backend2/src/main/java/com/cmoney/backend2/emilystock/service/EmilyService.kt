package com.cmoney.backend2.emilystock.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
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
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getEmilyCommKeys(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetEmilyCommKeysResponse>

    /**
     * 取得艾蜜莉股票清單詳細資訊
     */
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getStockInfos(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("teacherDefault") isTeacherDefault: Boolean,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetStockInfosResponse>

    /**
     * 取得指定股票艾蜜莉股票資訊
     */
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getTargetStockInfos(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("teacherDefault") isTeacherDefault: Boolean,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("CommKeys") commKeys: List<String>
    ): Response<GetTargetStockInfosWithError>

    /**
     * 取得指定股票的體質評估
     */
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getTargetConstitution(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("teacherDefault") isTeacherDefault: Boolean,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("CommKey") commKey: String
    ): Response<GetTargetConstitutionWithError>

    /**
     * 取得篩選條件
     */
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getFilterCondition(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetFilterConditionResponse>

    /**
     * 取得某會員的紅綠燈紀錄(需要再測試)
     */
    @RecordApi
    @FormUrlEncoded
    @POST
    suspend fun getTrafficLightRecord(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetTrafficLightRecordWithError>
}
