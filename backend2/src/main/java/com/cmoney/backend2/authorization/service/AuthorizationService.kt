package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.ExpiredTime
import com.cmoney.backend2.base.model.calladapter.RecordApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * AuthorizationService
 */
interface AuthorizationService {
    /**
     *取得會員某種權限類型的基底功能權限到期日(到期日是沒加時區的Utc時戳)
     *
     * @param type 權限類型 (手機請使用 MobilePaid)
     * @param subjectId appId
     *
     */
    @RecordApi
    @GET("AuthorizationServer/Authorization/ExpiredTime/{type}/{subjectId}")
    suspend fun getExpiredTime(
        @Header("Authorization") authorization: String,
        @Path("type") type: String,
        @Path("subjectId") subjectId: Int
    ): Response<ExpiredTime>
}