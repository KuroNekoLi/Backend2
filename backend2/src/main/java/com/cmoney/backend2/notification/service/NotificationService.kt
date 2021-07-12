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

interface NotificationService {
    /**
     * 新增訪客Token
     */
    @RecordApi
    @POST("NotificationService/DeviceToken/guest")
    suspend fun updateGuestPushToken(
        @Header("Authorization") authorization: String,
        @Body body: UpdateGuestTokenRequestBody
    ): Response<Void>

    /**
     * 新增會員Token
     */
    @RecordApi
    @POST("NotificationService/DeviceToken/member")
    suspend fun updateMemberPushToken(
        @Header("Authorization") authorization: String,
        @Body body: UpdateMemberTokenRequestBody
    ): Response<Void>

    /**
     * 增加點擊數
     */
    @RecordApi
    @POST("NotificationService/Statistics/clicked")
    suspend fun updateClickCount(
        @Header("Authorization") authorization: String,
        @Body body: UpdateClickedCountRequestBody
    ): Response<Void>

    /**
     * 增加到達數
     */
    @RecordApi
    @POST("NotificationService/Statistics/arrived")
    suspend fun updateArriveCount(
        @Header("Authorization") authorization: String,
        @Body body: UpdateArrivedRequestBody
    ): Response<Void>
}

