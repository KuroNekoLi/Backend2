package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.userbehavior.service.api.log.LogRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface UserBehaviorService {
    /**
     * 上傳報告
     */
    @RecordApi
    @POST
    suspend fun uploadReport(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: LogRequestBody
    ): Response<Void>
}