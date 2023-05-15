package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: Notification2Service,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : Notification2Web {

    override suspend fun <T> getHistoryNotifyAll(
        parameterClass: Class<T>,
        domain: String,
        url: String
    ): Result<List<GetNotifyAllResponseBody>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getHistoryNotifyAll(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId()
                )
                response.checkResponseBody(gson).formatCustomParameter(gson, parameterClass)
            }
        }

    override suspend fun getBranchFcm(
        domain: String,
        url: String
    ): Result<List<BranchSettingRequestBody>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getBranchFcm(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun updateBranchFcm(
        pushSettingId: Int,
        isNeedPush: Boolean,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.updateBranchFcm(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = UpdateBranchFcmRequestBody(
                    isNeedPush = isNeedPush,
                    pushSettingId = pushSettingId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            )
            response.checkResponseBody(gson)
            Unit
        }
    }

    override suspend fun updateBranchFcmMultipleSettings(
        pushSettings: List<PushSetting>,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.updateBranchFcmMultipleSettings(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = UpdateBranchFcmListRequestBody(
                    pushSettings = pushSettings,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getClubFcm(
        clubId: Long,
        domain: String,
        url: String
    ): Result<List<ClubFcmSettingResponseBody>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getClubFcm(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    clubId = clubId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun updateClubFcm(
        pushSettingType: Int,
        clubId: Long,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateClubFcm(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = UpdateClubFcmRequestBody(
                        pushSettingType = pushSettingType,
                        clubId = clubId,
                        guid = manager.getIdentityToken().getMemberGuid(),
                        appId = manager.getAppId()
                    )
                )
                response.checkResponseBody(gson)
                Unit
            }
        }

    override suspend fun getMainFcm(
        domain: String,
        url: String
    ): Result<GetMainFCMResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getMainFcm(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
                response.checkResponseBody(gson)
            }
        }

    override suspend fun updateMainFcm(
        isNeedPush: Boolean,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateMainFcm(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = UpdateMainFcmRequestBody(
                        isNeedPush = isNeedPush,
                        guid = manager.getIdentityToken().getMemberGuid(),
                        appId = manager.getAppId()
                    )
                )
                response.checkResponseBody(gson)
                Unit
            }
        }

    override suspend fun getMonitorList(
        domain: String,
        url: String
    ): Result<List<GetMonitorResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMonitorList(
                    guid = manager.getIdentityToken().getMemberGuid(),
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun insertMonitor(
        commonKey: String,
        strategyId: Int,
        monitorPrice: Double,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.insertMonitor(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = InsertMonitorRequestBody(
                    commonKey = commonKey,
                    strategyId = strategyId,
                    condition = InsertMonitorRequestBody.Condition(
                        targetPrice = monitorPrice
                    ),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun updateMonitor(
        conditionId: Long,
        strategyId: Int,
        monitorPrice: Double,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.updateMonitor(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = UpdateMonitorRequestBody(
                    conditionId = conditionId,
                    strategyId = strategyId,
                    condition = UpdateMonitorRequestBody.Condition(
                        targetPrice = monitorPrice
                    ),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun deleteMonitor(
        conditionId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteMonitor(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = DeleteMonitorRequestBody(
                    conditionId = conditionId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getMonitorHistoryList(
        domain: String,
        url: String
    ): Result<List<GetMonitorHistoryResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMonitorHistoryList(
                    guid = manager.getIdentityToken().getMemberGuid(),
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun updateMonitorPushNotification(
        conditionId: Long,
        isNeedPush: Boolean,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.updateMonitorPushNotification(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = UpdateMonitorPushNotificationRequestBody(
                    conditionId = conditionId,
                    isNeedToPush = isNeedPush,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getMrOptionOptionList(
        domain: String,
        url: String
    ): Result<List<GetMrOptionListResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMrOptionOptionConditionList(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun getMrOptionSpotGoodsList(
        domain: String,
        url: String
    ): Result<List<GetMrOptionListResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMrOptionSpotGoodsConditionList(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun updateMrOptionList(
        conditions: List<UpdateMrOptionConditionRequestBody.Condition>,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateMrOptionConditionList(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    updateMrOptionConditionRequestBody = UpdateMrOptionConditionRequestBody(
                        conditions = conditions,
                        appId = manager.getAppId()
                    )
                ).handleNoContent(gson)
            }
        }
}