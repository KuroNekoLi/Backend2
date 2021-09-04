package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.userbehavior.service.api.log.LogRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserBehaviorService {
    /**
     * 新增監控
     */
    @RecordApi
    @POST("UserBehaviorLog/Log")
    suspend fun uploadReport(
        @Header("Authorization") authorization: String,
        @Body requestBody: LogRequestBody
    ): Response<Void>
}