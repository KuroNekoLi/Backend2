package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.hasauth.Auth
import com.cmoney.backend2.base.model.calladapter.RecordApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * AuthorizationService
 */
interface AuthorizationService {
    /**
     *取得會員某種權限類型的基底功能權限到期日(到期日是沒加時區的Utc時戳)
     *
     * @param type 權限類型
     * @param subjectId appId
     *
     */
    @RecordApi
    @GET("AuthorizationServer/Authorization/ExpiredTime/{type}/{subjectId}")
    suspend fun getExpiredTime(
        @Header("Authorization") authorization: String,
        @Path("type") type: String,
        @Path("subjectId") subjectId: Long
    ): Response<ExpiredTime>

    @RecordApi
    @GET
    suspend fun hasAuth(
        @Header("Authorization") authorization: String,
        @Url url: String
    ): Response<Auth>


    /**
     * 取得會員指定類型有權限的基底功能清單
     */
    @RecordApi
    @GET("AuthorizationServer/Authorization/{type}/SubjectIds")
    suspend fun getPurchasedSubjectIds(
        @Header("Authorization") authorization: String,
        @Path("type") type: String
    ): Response<List<Long>>
}
