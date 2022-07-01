package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.commonuse.service.api.query.QueryParam
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * <a href="https://outpost.cmoney.tw/commonuse/graphql/">Document</a>
 */
interface CommonUseService {

    @RecordApi
    @POST
    suspend fun query(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body query: QueryParam
    ): Response<JsonObject>
}
