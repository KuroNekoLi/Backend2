package com.cmoney.backend2.trial.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.trial.service.api.checktrialtime.CheckTrialTimeResponseBody
import com.cmoney.backend2.trial.service.api.gettrialquota.GetTrialQuotaResponseBody
import com.cmoney.backend2.trial.service.api.setquotause.SetQuotaUseResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface TrialService {

    /**
     * 呼叫時就會固定扣除1次的試用次數
     */
    @RecordApi
    @FormUrlEncoded
    @POST("Authentication/trial-quota/usage/use")
    suspend fun setQuotaUsageUse(
            @Header("Authorization") authorization: String,
            @Field("AppId") appId: Int,
            @Field("Guid") guid: String,
            @Field("trialKey") trialKey: String
    ): Response<SetQuotaUseResponseBody>

    /**
     * 呼叫時就會扣除[quotaUsed]秒的時間
     */
    @RecordApi
    @FormUrlEncoded
    @POST("Authentication/trial-quota/time/use")
    suspend fun setQuotaTimeUse(
        @Header("Authorization") authorization: String,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("TrialKey") trialKey: String,
        @Field("QuotaUsed") quotaUsed: Int
    ): Response<SetQuotaUseResponseBody>

    /**
     * 呼叫時就開始試用
     * 在這同時會設定到期時間為: 現在時間 + 可試用時間
     * 第二次起的呼叫就會去計算離到期時間還有幾秒
     */
    @RecordApi
    @FormUrlEncoded
    @POST("Authentication/trial-time/check")
    suspend fun checkTrialTime(
            @Header("Authorization") authorization: String,
            @Field("AppId") appId: Int,
            @Field("Guid") guid: String,
            @Field("trialKey") trialKey: String
    ): Response<CheckTrialTimeResponseBody>

    /**
     * 取得該會員在該試用金鑰剩餘次數(時間)
     */
    @RecordApi
    @FormUrlEncoded
    @POST("Authentication/trial-quota/get")
    suspend fun getTrialQuota(
            @Header("Authorization") authorization: String,
            @Field("AppId") appId: Int,
            @Field("Guid") guid: String,
            @Field("trialKey") trialKey: String
    ): Response<GetTrialQuotaResponseBody>
}