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
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun <T> getHistoryNotifyAll(
        parameterClass: Class<T>,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/History/NotifyAll"
    ): Result<List<GetNotifyAllResponseBody>>

    /**
     * 取得分支推播設定
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun getBranchFcm(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Branch"
    ): Result<List<BranchSettingRequestBody>>

    /**
     * 更新分支推播設定
     *
     * @param pushSettingId 分支設定編號
     * @param isNeedPush 是否需要推播
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun updateBranchFcm(
        pushSettingId: Int,
        isNeedPush: Boolean,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Branch"
    ): Result<Unit>

    /**
     * 更新多筆分支推播設定
     *
     * @param pushSettings 分支推播設定集合
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun updateBranchFcmMultipleSettings(
        pushSettings: List<PushSetting>,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Branch"
    ): Result<Unit>

    /**
     * 取得社團推播設定
     *
     * @param clubId 社團編號
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun getClubFcm(
        clubId: Long,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Club"
    ): Result<List<ClubFcmSettingResponseBody>>

    /**
     * 更新社團推播設定
     *
     * @param pushSettingType setting種類, 0: 全部關閉 3: 全部打開
     * @param clubId 社團編號
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun updateClubFcm(
        pushSettingType: Int,
        clubId: Long,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Club"
    ): Result<Unit>

    /**
     * 取得主要推播設定
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun getMainFcm(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Main"
    ): Result<GetMainFCMResponseBody>

    /**
     * 更新主要推播設定
     *
     * @param isNeedPush 是否推播
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun updateMainFcm(
        isNeedPush: Boolean,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/usersetting/Main"
    ): Result<Unit>

    /**
     * 取得目前App的監控列表
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getMonitorList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor"
    ) : Result<List<GetMonitorResponseBody>>

    /**
     * 新增監控
     *
     * @param commonKey 標的
     * @param strategyId 監控策略編號
     * @param monitorPrice 價位
     * @param domain 網域名稱
     * @param url 完整的Url
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
     *
     * @param conditionId 使用者條件編號
     * @param strategyId 監控策略編號
     * @param monitorPrice 價位
     * @param domain 網域名稱
     * @param url 完整的Url
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
     *
     * @param conditionId 使用者條件編號
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun deleteMonitor(
        conditionId: Long,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor"
    ) : Result<Unit>

    /**
     * 拿到監控推播歷史
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getMonitorHistoryList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/Monitor/history"
    ) : Result<List<GetMonitorHistoryResponseBody>>

    /**
     * 更新監控推播
     *
     * @param conditionId 使用者條件編號
     * @param isNeedPush 是否推播
     * @param domain 網域名稱
     * @param url 完整的Url
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
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return List<MrOptionCondition>
     */
    suspend fun getMrOptionOptionList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/MrOption/Option"
    ): Result<List<GetMrOptionListResponseBody>>

    /**
     * 取得期權先生現貨條件設定
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return List<MrOptionCondition>
     */
    suspend fun getMrOptionSpotGoodsList(
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/MrOption/SpotGoods"
    ): Result<List<GetMrOptionListResponseBody>>

    /**
     * 更新期權先生條件
     *
     * @param conditions List<Condition> 用戶的所有條件
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun updateMrOptionList(
        conditions: List<UpdateMrOptionConditionRequestBody.Condition>,
        domain: String = manager.getNotification2SettingAdapter().getDomain(),
        url: String = "${domain}notification/userCondition/MrOption"
    ): Result<Unit>
}