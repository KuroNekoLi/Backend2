package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.notification2.service.api.deletemonitor.DeleteMonitorRequestBody
import com.cmoney.backend2.notification2.service.api.getbranchfcm.BranchSettingRequestBody
import com.cmoney.backend2.notification2.service.api.getclubfcm.ClubFcmSettingResponseBody
import com.cmoney.backend2.notification2.service.api.gethistorynotifyall.GetNotifyAllResponseBody
import com.cmoney.backend2.notification2.service.api.gethistorynotifyall.formatCustomParameter
import com.cmoney.backend2.notification2.service.api.getmainfcm.GetMainFCMResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitor.GetMonitorResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitorhistory.GetMonitorHistoryResponseBody
import com.cmoney.backend2.notification2.service.api.getmroptionlist.GetMrOptionListResponseBody
import com.cmoney.backend2.notification2.service.api.insertmonitor.InsertMonitorRequestBody
import com.cmoney.backend2.notification2.service.api.updateMonitor.UpdateMonitorRequestBody
import com.cmoney.backend2.notification2.service.api.updatebranchfcm.UpdateBranchFcmRequestBody
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.PushSetting
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.UpdateBranchFcmListRequestBody
import com.cmoney.backend2.notification2.service.api.updateclubfcm.UpdateClubFcmRequestBody
import com.cmoney.backend2.notification2.service.api.updatemainfcm.UpdateMainFcmRequestBody
import com.cmoney.backend2.notification2.service.api.updatemonitorpushnotification.UpdateMonitorPushNotificationRequestBody
import com.cmoney.backend2.notification2.service.api.updatemroptionlist.UpdateMrOptionConditionRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class Notification2WebImpl(
    private val gson: Gson,
    private val service: Notification2Service,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : Notification2Web {

    override suspend fun <T> getNotifyHistory(appId: Int, parameterClass: Class<T>): Result<List<GetNotifyAllResponseBody>> =
        getHistoryNotifyAll(parameterClass)

    override suspend fun <T> getHistoryNotifyAll(parameterClass: Class<T>): Result<List<GetNotifyAllResponseBody>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getHistoryNotifyAll(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId
                )
                response.checkResponseBody(gson).formatCustomParameter(gson, parameterClass)
            }
        }


    override suspend fun getBranchFcm(
        apiParam: MemberApiParam
    ): Result<List<BranchSettingRequestBody>> = getBranchFcm()

    override suspend fun getBranchFcm(): Result<List<BranchSettingRequestBody>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getBranchFcm(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun updateBranchFcm(
        pushSettingId: Int,
        isNeedPush: Boolean,
        apiParam: MemberApiParam
    ): Result<Unit> = updateBranchFcm(pushSettingId, isNeedPush)

    override suspend fun updateBranchFcm(
        pushSettingId: Int,
        isNeedPush: Boolean
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.updateBranchFcm(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = UpdateBranchFcmRequestBody(
                    isNeedPush = isNeedPush,
                    pushSettingId = pushSettingId,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
            )
            response.checkResponseBody(gson)
            Unit
        }
    }

    override suspend fun updateBranchFcmMultipleSettings(
        pushSettings: List<PushSetting>,
        apiParam: MemberApiParam
    ): Result<Unit> = updateBranchFcmMultipleSettings(pushSettings)

    override suspend fun updateBranchFcmMultipleSettings(
        pushSettings: List<PushSetting>
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.updateBranchFcmMultipleSettings(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = UpdateBranchFcmListRequestBody(
                    pushSettings = pushSettings,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getClubFcm(
        clubId: Long,
        apiParam: MemberApiParam
    ): Result<List<ClubFcmSettingResponseBody>> = getClubFcm(clubId)

    override suspend fun getClubFcm(clubId: Long): Result<List<ClubFcmSettingResponseBody>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getClubFcm(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    clubId = clubId,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun updateClubFcm(
        pushSettingType: Int,
        clubId: Long,
        apiParam: MemberApiParam
    ): Result<Unit> = updateClubFcm(pushSettingType, clubId)

    override suspend fun updateClubFcm(pushSettingType: Int, clubId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateClubFcm(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = UpdateClubFcmRequestBody(
                        pushSettingType = pushSettingType,
                        clubId = clubId,
                        guid = setting.identityToken.getMemberGuid(),
                        appId = setting.appId
                    )
                )
                response.checkResponseBody(gson)
                Unit
            }
        }

    override suspend fun getMainFcm(
        apiParam: MemberApiParam
    ): Result<GetMainFCMResponseBody> = getMainFcm()

    override suspend fun getMainFcm(): Result<GetMainFCMResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getMainFcm(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
                response.checkResponseBody(gson)
            }
        }


    override suspend fun updateMainFcm(
        isNeedPush: Boolean,
        apiParam: MemberApiParam
    ): Result<Unit> = updateMainFcm(isNeedPush)

    override suspend fun updateMainFcm(isNeedPush: Boolean): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateMainFcm(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = UpdateMainFcmRequestBody(
                        isNeedPush = isNeedPush,
                        guid = setting.identityToken.getMemberGuid(),
                        appId = setting.appId
                    )
                )
                response.checkResponseBody(gson)
                Unit
            }
        }

    override suspend fun getMonitorConditionList(apiParam: MemberApiParam): Result<List<GetMonitorResponseBody>> =
        getMonitorList()

    override suspend fun getMonitorList(): Result<List<GetMonitorResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMonitorList(
                    guid = setting.identityToken.getMemberGuid(),
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId
                ).checkResponseBody(gson)
            }
        }

    override suspend fun addNewMonitorCondition(
        commonKey: String,
        strategyId: Int,
        monitorPrice: Double,
        apiParam: MemberApiParam
    ): Result<Unit> = insertMonitor(commonKey, strategyId, monitorPrice)

    override suspend fun insertMonitor(
        commonKey: String,
        strategyId: Int,
        monitorPrice: Double
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {

            service.insertMonitor(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = InsertMonitorRequestBody(
                    commonKey = commonKey,
                    strategyId = strategyId,
                    condition = InsertMonitorRequestBody.Condition(
                        targetPrice = monitorPrice
                    ),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun updateCondition(
        conditionId: Long,
        strategyId: Int,
        monitorPrice: Double,
        apiParam: MemberApiParam
    ): Result<Unit> = updateMonitor(
        conditionId,
        strategyId,
        monitorPrice
    )

    override suspend fun updateMonitor(
        conditionId: Long,
        strategyId: Int,
        monitorPrice: Double
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.updateMonitor(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = UpdateMonitorRequestBody(
                    conditionId = conditionId,
                    strategyId = strategyId,
                    condition = UpdateMonitorRequestBody.Condition(
                        targetPrice = monitorPrice
                    ),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun deleteMonitorCondition(
        conditionId: Long,
        apiParam: MemberApiParam
    ): Result<Unit> = deleteMonitor(conditionId)

    override suspend fun deleteMonitor(
        conditionId: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteMonitor(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = DeleteMonitorRequestBody(
                    conditionId = conditionId,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getMonitorArriveNotifyHistoryList(apiParam: MemberApiParam):
            Result<List<GetMonitorHistoryResponseBody>> = getMonitorHistoryList()


    override suspend fun getMonitorHistoryList(): Result<List<GetMonitorHistoryResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMonitorHistoryList(
                    guid = setting.identityToken.getMemberGuid(),
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId
                ).checkResponseBody(gson)
            }
        }

    override suspend fun updateMonitorIsNeedToPush(
        conditionId: Long,
        isNeedPush: Boolean,
        apiParam: MemberApiParam
    ): Result<Unit> = updateMonitorPushNotification(conditionId, isNeedPush)

    override suspend fun updateMonitorPushNotification(
        conditionId: Long,
        isNeedPush: Boolean
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.updateMonitorPushNotification(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = UpdateMonitorPushNotificationRequestBody(
                    conditionId = conditionId,
                    isNeedToPush = isNeedPush,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getMrOptionOptionConditionList(
        apiParam: MemberApiParam
    ): Result<List<GetMrOptionListResponseBody>> = getMrOptionOptionList()

    override suspend fun getMrOptionOptionList(): Result<List<GetMrOptionListResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMrOptionOptionConditionList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                ).checkResponseBody(gson)
            }
        }

    override suspend fun getMrOptionSpotGoodsConditionList(
        apiParam: MemberApiParam
    ): Result<List<GetMrOptionListResponseBody>> = getMrOptionSpotGoodsList()

    override suspend fun getMrOptionSpotGoodsList(): Result<List<GetMrOptionListResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMrOptionSpotGoodsConditionList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                ).checkResponseBody(gson)
            }
        }

    override suspend fun updateMrOptionConditionList(
        conditions: List<UpdateMrOptionConditionRequestBody.Condition>,
        apiParam: MemberApiParam
    ): Result<Unit> = updateMrOptionList(conditions)

    override suspend fun updateMrOptionList(conditions: List<UpdateMrOptionConditionRequestBody.Condition>): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateMrOptionConditionList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    updateMrOptionConditionRequestBody = UpdateMrOptionConditionRequestBody(
                        conditions = conditions,
                        appId = setting.appId
                    )
                ).handleNoContent(gson)
            }
        }
}