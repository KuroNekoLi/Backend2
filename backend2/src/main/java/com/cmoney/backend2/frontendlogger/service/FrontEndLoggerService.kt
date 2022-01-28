package com.cmoney.backend2.frontendlogger.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.frontendlogger.service.api.LogRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * FrontEndLogger Service
 *
 * @see <a href="https://outpost.cmoney.tw/frontendlogger/swagger/index.html">api doc</a>
 */
interface FrontEndLoggerService {

    @RecordApi
    @POST
    suspend fun log(
        @Url url: String,
        @Header("Authorization") authToken: String,
        @Body body: List<LogRequestBody>
    ): Response<Void>

}
