package com.cmoney.backend2.clientconfiguration.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ClientConfigurationService {

    /**
     * 取得設定檔
     *
     *  @param keys Key list
     */
    @RecordApi
    @POST
    suspend fun getConfig(
        @Url url: String,
        @Body keys: List<String>
    ): Response<ClientConfigResponseBody>

}