package com.cmoney.backend2.notification.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.notification.service.api.devicetoken.updateguesttoken.UpdateGuestTokenRequestBody
import com.cmoney.backend2.notification.service.api.devicetoken.updatemembertoken.UpdateMemberTokenRequestBody
import com.cmoney.backend2.notification.service.api.statistics.updatearrived.UpdateArrivedRequestBody
import com.cmoney.backend2.notification.service.api.statistics.updateclickcount.UpdateClickedCountRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface NotificationService {

    /**
     * 增加到達數
     */
    @RecordApi
    @POST
    suspend fun updateArriveCount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: UpdateArrivedRequestBody
    ): Response<Void>

    /**
     * 新增訪客Token
     */
    @RecordApi
    @POST
    suspend fun updateGuestPushToken(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: UpdateGuestTokenRequestBody
    ): Response<Void>

    /**
     * 新增會員Token
     */
    @RecordApi
    @POST
    suspend fun updateMemberPushToken(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: UpdateMemberTokenRequestBody
    ): Response<Void>

    /**
     * 增加點擊數
     */
    @RecordApi
    @POST
    suspend fun updateClickCount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: UpdateClickedCountRequestBody
    ): Response<Void>
}

