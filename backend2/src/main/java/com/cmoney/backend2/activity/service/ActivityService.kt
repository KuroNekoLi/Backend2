package com.cmoney.backend2.activity.service

import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountRequestBody
import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountResponseBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountRequestBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountResponseBody
import com.cmoney.backend2.activity.service.api.requestbonus.RequestBonusRequestBody
import com.cmoney.backend2.base.model.calladapter.RecordApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ActivityService {
    /**
     * 取得用戶這個月開啟幾天APP
     */
    @RecordApi
    @POST
    suspend fun getDayCount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: GetDayCountRequestBody
    ): Response<GetDayCountResponseBody>

    /**
     * 請求獎勵
     */
    @RecordApi
    @POST
    suspend fun requestBonus(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBonusRequestBody
    ): Response<Void>

    /**
     * 取得推薦人總共成功推薦次數
     */
    @RecordApi
    @POST
    suspend fun getReferralCount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: GetReferralCountRequestBody
    ): Response<GetReferralCountResponseBody>
}