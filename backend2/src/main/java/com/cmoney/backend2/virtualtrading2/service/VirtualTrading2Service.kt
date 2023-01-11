package com.cmoney.backend2.virtualtrading2.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountResponseBody
import retrofit2.Response
import retrofit2.http.*

interface VirtualTrading2Service {
    /**
     * 創建帳戶
     */
    @RecordApi
    @POST
    suspend fun createAccount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CreateAccountRequestBody
    ): Response<CreateAccountResponseBody>
}