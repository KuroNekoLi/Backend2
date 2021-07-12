package com.cmoney.backend2.crm.service

import com.cmoney.backend2.crm.service.api.creatlivechat.CreateLiveChatRequestBody
import com.cmoney.backend2.crm.service.api.creatlivechat.CreateLiveChatResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CrmService {
    /**
     * 建立與客服對話視窗
     */
    @POST("CRM/livechat")
    suspend fun createLiveChat(
        @Header("Authorization") authorization: String,
        @Body requestBody: CreateLiveChatRequestBody
    ): Response<CreateLiveChatResponseBody>
}