package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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

    val manager: GlobalBackend2Manager

    /**
     * 取得所有人都有的通知
     */
    suspend fun <T> getHistoryNotifyAll(
        parameterClass: Class<T>,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/History/NotifyAll"
    ): Result<List<GetNotifyAllResponseBody>>

    suspend fun getBranchFcm(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Branch"
    ): Result<List<BranchSettingRequestBody>>

    suspend fun updateBranchFcm(
        pushSettingId: Int,
        isNeedPush: Boolean,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Branch"
    ): Result<Unit>

    suspend fun updateBranchFcmMultipleSettings(
        pushSettings: List<PushSetting>,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Branch"
    ): Result<Unit>

    suspend fun getClubFcm(
        clubId: Long,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Club"
    ): Result<List<ClubFcmSettingResponseBody>>

    suspend fun updateClubFcm(
        pushSettingType: Int,
        clubId: Long,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Club"
    ): Result<Unit>

    suspend fun getMainFcm(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Main"
    ): Result<GetMainFCMResponseBody>

    suspend fun updateMainFcm(
        isNeedPush: Boolean,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Main"
    ): Result<Unit>

    /**
     * 取的某個App的監控列表
     */
    suspend fun getMonitorList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor"
    ) : Result<List<GetMonitorResponseBody>>

    /**
     * 新增監控
     */
    suspend fun insertMonitor(
        commonKey : String,
        strategyId : Int,
        monitorPrice : Double,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor"
    ) : Result<Unit>

    /**
     * 更新監控
     */
    suspend fun updateMonitor(
        conditionId : Long,
        strategyId : Int,
        monitorPrice : Double,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor"
    ) : Result<Unit>

    /**
     * 刪除監控
     */
    suspend fun deleteMonitor(
        conditionId: Long,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor"
    ) : Result<Unit>

    /**
     * 拿到監控推播歷史
     */
    suspend fun getMonitorHistoryList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor/history"
    ) : Result<List<GetMonitorHistoryResponseBody>>

    /**
     * 更新監控推播
     */
    suspend fun updateMonitorPushNotification(
        conditionId: Long,
        isNeedPush: Boolean,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor/isNeedPush"
    ) : Result<Unit>


    // MrOption-----------------------

    /**
     * 取得期權先生期權條件設定
     *
     * @return List<MrOptionCondition>
     */
    suspend fun getMrOptionOptionList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/MrOption/Option"
    ): Result<List<GetMrOptionListResponseBody>>

    /**
     * 取得期權先生現貨條件設定
     *
     * @return List<MrOptionCondition>
     */
    suspend fun getMrOptionSpotGoodsList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/MrOption/SpotGoods"
    ): Result<List<GetMrOptionListResponseBody>>

    /**
     * 更新期權先生條件
     *
     * @param conditions List<Condition>
     */
    suspend fun updateMrOptionList(
        conditions: List<UpdateMrOptionConditionRequestBody.Condition>,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/MrOption"
    ): Result<Unit>
}