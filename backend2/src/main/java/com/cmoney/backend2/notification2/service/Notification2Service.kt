package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.notification2.service.api.deletemonitor.DeleteMonitorRequestBody
import com.cmoney.backend2.notification2.service.api.getbranchfcm.BranchSettingRequestBody
import com.cmoney.backend2.notification2.service.api.getclubfcm.ClubFcmSettingResponseBody
import com.cmoney.backend2.notification2.service.api.gethistorynotifyall.GetNotifyAllResponseBody
import com.cmoney.backend2.notification2.service.api.getmainfcm.GetMainFCMResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitor.GetMonitorResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitorhistory.GetMonitorHistoryResponseBody
import com.cmoney.backend2.notification2.service.api.getmroptionlist.GetMrOptionListResponseBody
import com.cmoney.backend2.notification2.service.api.insertmonitor.InsertMonitorRequestBody
import com.cmoney.backend2.notification2.service.api.updateMonitor.UpdateMonitorRequestBody
import com.cmoney.backend2.notification2.service.api.updatebranchfcm.UpdateBranchFcmRequestBody
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.UpdateBranchFcmListRequestBody
import com.cmoney.backend2.notification2.service.api.updateclubfcm.UpdateClubFcmRequestBody
import com.cmoney.backend2.notification2.service.api.updatemainfcm.UpdateMainFcmRequestBody
import com.cmoney.backend2.notification2.service.api.updatemonitorpushnotification.UpdateMonitorPushNotificationRequestBody
import com.cmoney.backend2.notification2.service.api.updatemroptionlist.UpdateMrOptionConditionRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface Notification2Service {

    /**
     * 取得所有人都有的通知
     */
    @RecordApi
    @GET
    suspend fun getHistoryNotifyAll(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("AppId") appId: Int
    ): Response<List<GetNotifyAllResponseBody>>

    /**
     * 取得推播分支設定(個別設定)
     */
    @RecordApi
    @GET
    suspend fun getBranchFcm(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Guid") guid: String,
        @Query("AppId") appId: Int
    ): Response<List<BranchSettingRequestBody>>

    /**
     * 更新推播分支設定(個別設定)
     */
    @RecordApi
    @PUT
    suspend fun updateBranchFcm(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: UpdateBranchFcmRequestBody
    ): Response<ResponseBody>

    /**
     * 更新推播分支設定(多組設定)
     */
    @RecordApi
    @PATCH
    suspend fun updateBranchFcmMultipleSettings(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: UpdateBranchFcmListRequestBody
    ): Response<Void>

    /**
     * 取得社團推播設定
     *
     */
    @RecordApi
    @GET
    suspend fun getClubFcm(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("ClubId") clubId: Long,
        @Query("Guid") guid: String,
        @Query("AppId") appId: Int
    ): Response<List<ClubFcmSettingResponseBody>>

    /**
     * 更新社團推播設定
     */
    @RecordApi
    @PUT
    suspend fun updateClubFcm(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: UpdateClubFcmRequestBody
    ): Response<ResponseBody>

    /**
     * 取得總推播設定
     */
    @RecordApi
    @GET
    suspend fun getMainFcm(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Guid") guid: String,
        @Query("AppId") appId: Int
    ): Response<GetMainFCMResponseBody>

    /**
     * 更新總推播設定
     */
    @RecordApi
    @PUT
    suspend fun updateMainFcm(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: UpdateMainFcmRequestBody
    ): Response<ResponseBody>

    /**
     * 取的某個App的監控列表
     */
    @RecordApi
    @GET
    suspend fun getMonitorList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Guid") guid: String,
        @Query("AppId") appId: Int
    ): Response<List<GetMonitorResponseBody>>

    /**
     * 新增監控
     */
    @RecordApi
    @POST
    suspend fun insertMonitor(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: InsertMonitorRequestBody
    ): Response<Void>

    /**
     * 更新監控
     */
    @RecordApi
    @PUT
    suspend fun updateMonitor(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: UpdateMonitorRequestBody
    ): Response<Void>

    /**
     * 刪除監控
     */
    @RecordApi
    @DELETE
    suspend fun deleteMonitor(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: DeleteMonitorRequestBody
    ): Response<Void>

    /**
     * 取得監控歷史列表
     */
    @RecordApi
    @GET
    suspend fun getMonitorHistoryList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Guid") guid: String,
        @Query("AppId") appId: Int
    ): Response<List<GetMonitorHistoryResponseBody>>

    /**
     * 更新監控推播
     */
    @RecordApi
    @PUT
    suspend fun updateMonitorPushNotification(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: UpdateMonitorPushNotificationRequestBody
    ): Response<Void>

    // MrOption-----------------------
    /**
     * 取得期權條件設定
     * @param guid String
     * @param appId Int
     * @return List<MrOptionCondition>
     */
    @RecordApi
    @GET
    suspend fun getMrOptionOptionConditionList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Guid") guid: String,
        @Query("AppId") appId: Int
    ): Response<List<GetMrOptionListResponseBody>>

    /**
     * 取得現貨條件設定
     * @param guid String
     * @param appId Int
     * @return List<MrOptionCondition>
     */
    @RecordApi
    @GET
    suspend fun getMrOptionSpotGoodsConditionList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Guid") guid: String,
        @Query("AppId") appId: Int
    ): Response<List<GetMrOptionListResponseBody>>

    /**
     * 更新條件
     * @param updateMrOptionConditionRequestBody UpdateMrOptionCondition
     */
    @RecordApi
    @PUT
    suspend fun updateMrOptionConditionList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body updateMrOptionConditionRequestBody: UpdateMrOptionConditionRequestBody
    ): Response<Void>

}