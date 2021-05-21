package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.notification2.service.api.getbranchfcm.BranchSettingRequestBody
import com.cmoney.backend2.notification2.service.api.getclubfcm.ClubFcmSettingResponseBody
import com.cmoney.backend2.notification2.service.api.gethistorynotifyall.GetNotifyAllResponseBody
import com.cmoney.backend2.notification2.service.api.getmainfcm.GetMainFCMResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitor.GetMonitorResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitorhistory.GetMonitorHistoryResponseBody
import com.cmoney.backend2.notification2.service.api.getmroptionlist.GetMrOptionListResponseBody
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.PushSetting
import com.cmoney.backend2.notification2.service.api.updatemroptionlist.UpdateMrOptionConditionRequestBody

interface Notification2Web {

    /**
     * 取得所有人都有的通知
     * @see parameterClass 依App parameter回傳不同參數, 自行設計資料物件
     */
    @Deprecated("AppId no longer required. Use getHistoryNotifyAll()")
    suspend fun <T> getNotifyHistory(
        appId: Int,
        parameterClass: Class<T>
    ): Result<List<GetNotifyAllResponseBody>>

    /**
     * 取得所有人都有的通知
     */
    suspend fun <T> getHistoryNotifyAll(parameterClass: Class<T>): Result<List<GetNotifyAllResponseBody>>

    @Deprecated("ApiParam no longer required")
    suspend fun getBranchFcm(
        apiParam: MemberApiParam
    ): Result<List<BranchSettingRequestBody>>

    suspend fun getBranchFcm(): Result<List<BranchSettingRequestBody>>

    @Deprecated("ApiParam no longer required")
    suspend fun updateBranchFcm(
        pushSettingId: Int,
        isNeedPush: Boolean,
        apiParam: MemberApiParam
    ): Result<Unit>

    suspend fun updateBranchFcm(
        pushSettingId: Int,
        isNeedPush: Boolean
    ): Result<Unit>

    @Deprecated("ApiParam no longer required")
    suspend fun updateBranchFcmMultipleSettings(
        pushSettings: List<PushSetting>,
        apiParam: MemberApiParam
    ): Result<Unit>

    suspend fun updateBranchFcmMultipleSettings(
        pushSettings: List<PushSetting>
    ): Result<Unit>

    @Deprecated("ApiParam no longer required")
    suspend fun getClubFcm(
        clubId: Long,
        apiParam: MemberApiParam
    ): Result<List<ClubFcmSettingResponseBody>>

    suspend fun getClubFcm(
        clubId: Long
    ): Result<List<ClubFcmSettingResponseBody>>

    @Deprecated("ApiParam no longer required")
    suspend fun updateClubFcm(
        pushSettingType: Int,
        clubId: Long,
        apiParam: MemberApiParam
    ): Result<Unit>

    suspend fun updateClubFcm(
        pushSettingType: Int,
        clubId: Long
    ): Result<Unit>

    @Deprecated("ApiParam no longer required")
    suspend fun getMainFcm(
        apiParam: MemberApiParam
    ): Result<GetMainFCMResponseBody>

    suspend fun getMainFcm(): Result<GetMainFCMResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun updateMainFcm(
        isNeedPush: Boolean,
        apiParam: MemberApiParam
    ): Result<Unit>

    suspend fun updateMainFcm(isNeedPush: Boolean): Result<Unit>

    /**
     * 取的某個App的監控列表
     */
    @Deprecated("ApiParam no longer required. Use getMonitorList()")
    suspend fun getMonitorConditionList(
        apiParam: MemberApiParam
    ) : Result<List<GetMonitorResponseBody>>

    /**
     * 取的某個App的監控列表
     */
    suspend fun getMonitorList() : Result<List<GetMonitorResponseBody>>

    /**
     * 新增監控
     */
    @Deprecated("ApiParam no longer required.Use insertMonitor()")
    suspend fun addNewMonitorCondition(
        commonKey : String,
        strategyId : Int,
        monitorPrice : Double,
        apiParam: MemberApiParam
    ) : Result<Unit>

    /**
     * 新增監控
     */
    suspend fun insertMonitor(
        commonKey : String,
        strategyId : Int,
        monitorPrice : Double
    ) : Result<Unit>

    /**
     * 更新監控
     */
    @Deprecated("ApiParam no longer required.Use updateMonitor()")
    suspend fun updateCondition(
        conditionId : Long,
        strategyId : Int,
        monitorPrice : Double,
        apiParam: MemberApiParam
    ) : Result<Unit>

    /**
     * 更新監控
     */
    suspend fun updateMonitor(
        conditionId : Long,
        strategyId : Int,
        monitorPrice : Double
    ) : Result<Unit>

    /**
     * 刪除監控
     */
    @Deprecated("ApiParam no longer required. User deleteMonitor()")
    suspend fun deleteMonitorCondition(
        conditionId: Long,
        apiParam: MemberApiParam
    ) : Result<Unit>

    /**
     * 刪除監控
     */
    suspend fun deleteMonitor(
        conditionId: Long
    ) : Result<Unit>

    /**
     * 拿到監控推播歷史
     */
    @Deprecated("ApiParam no longer required.Use getMonitorHistoryList()")
    suspend fun getMonitorArriveNotifyHistoryList(
        apiParam: MemberApiParam
    ) : Result<List<GetMonitorHistoryResponseBody>>

    /**
     * 拿到監控推播歷史
     */
    suspend fun getMonitorHistoryList() : Result<List<GetMonitorHistoryResponseBody>>

    /**
     * 更新監控推播
     */
    @Deprecated("ApiParam no longer required.Use updateMonitorPushNotification()")
    suspend fun updateMonitorIsNeedToPush(
        conditionId: Long,
        isNeedPush: Boolean,
        apiParam: MemberApiParam
    ) : Result<Unit>

    /**
     * 更新監控推播
     */
    suspend fun updateMonitorPushNotification(
        conditionId: Long,
        isNeedPush: Boolean
    ) : Result<Unit>


    // MrOption-----------------------
    /**
     * 取得期權先生期權條件設定
     * @param apiParam MemberApiParam
     * @return List<MrOptionCondition>
     */
    @Deprecated("ApiParam no longer required.Use getMrOptionOptionList()")
    suspend fun getMrOptionOptionConditionList(
        apiParam: MemberApiParam
    ): Result<List<GetMrOptionListResponseBody>>

    /**
     * 取得期權先生期權條件設定
     * @return List<MrOptionCondition>
     */
    suspend fun getMrOptionOptionList(): Result<List<GetMrOptionListResponseBody>>

    /**
     * 取得期權先生現貨條件設定
     * @param apiParam MemberApiParam
     * @return List<MrOptionCondition>
     */
    @Deprecated("ApiParam no longer required.Use getMrOptionSpotGoodsList()")
    suspend fun getMrOptionSpotGoodsConditionList(
        apiParam: MemberApiParam
    ): Result<List<GetMrOptionListResponseBody>>

    /**
     * 取得期權先生現貨條件設定
     * @return List<MrOptionCondition>
     */
    suspend fun getMrOptionSpotGoodsList(): Result<List<GetMrOptionListResponseBody>>

    /**
     * 更新期權先生條件
     * @param conditions List<Condition>
     * @param apiParam MemberApiParam
     */
    @Deprecated("ApiParam no longer required.Use updateMrOptionList()")
    suspend fun updateMrOptionConditionList(
        conditions: List<UpdateMrOptionConditionRequestBody.Condition>,
        apiParam: MemberApiParam
    ): Result<Unit>

    /**
     * 更新期權先生條件
     * @param conditions List<Condition>
     */
    suspend fun updateMrOptionList(
        conditions: List<UpdateMrOptionConditionRequestBody.Condition>
    ): Result<Unit>
}