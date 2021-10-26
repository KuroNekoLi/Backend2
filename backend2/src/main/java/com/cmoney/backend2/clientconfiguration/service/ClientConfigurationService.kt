package com.cmoney.backend2.clientconfiguration.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ClientConfigurationService {

    /**
     * 取得設定檔
     *
     *  @param keys Key list , ex: KOL
     */
    @RecordApi
    @POST("ClientConfiguration/api/config/get")
    suspend fun getConfig(
        @Body keys: List<String>
    ): Response<ClientConfigResponseBody>

}