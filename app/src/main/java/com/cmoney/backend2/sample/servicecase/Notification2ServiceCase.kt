package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.notification2.service.Notification2Web
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.PushSetting
import com.cmoney.backend2.notification2.service.api.updatemroptionlist.UpdateMrOptionConditionRequestBody
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.sample.servicecase.data.Notification2TestParameter
import kotlinx.coroutines.delay
import org.koin.core.component.inject

/**
 * 推播開關服務測試
 *
 */
class Notification2ServiceCase : ServiceCase {

    private val userSettingImpl by inject<Notification2Web>()

    override suspend fun testAll() {
        userSettingImpl.apply {
            getHistoryNotifyAll(Notification2TestParameter::class.java)
                .logResponse(TAG)

            getBranchFcm()
                .logResponse(TAG)
            //推播設定ID
            val pushSettingId = 6
            updateBranchFcm(pushSettingId, true)
                .logResponse(TAG)
            //多組推播設定
            val pushSettings = listOf(
                PushSetting(true, 17),
                PushSetting(true, 18),
                PushSetting(true, 19)
            )
            updateBranchFcmMultipleSettings(pushSettings)
                .logResponse(TAG)
            val clubId = 105551L
            getClubFcm(clubId)
                .logResponse(TAG)
            val randType = (0..2).random()
            updateClubFcm(randType, clubId)
                .logResponse(TAG)
            getMainFcm()
                .logResponse(TAG)
            updateMainFcm(true)
                .logResponse(TAG)
            //Monitor
            delay(5000)
            val commonKey = "2330"
            getMonitorList()
                .logResponse(TAG)
            delay(1000)
            insertMonitor(commonKey, 1, 400.0)
                .logResponse(TAG)
            delay(1000)
            val monitorListResult = getMonitorList()
            monitorListResult.logResponse(TAG)
            val updateMonitorList = monitorListResult.getOrNull().orEmpty()
            delay(1000)
            val updateMonitor = updateMonitorList.find {
                it.commonKey.orEmpty() == commonKey
            }
            updateMonitor(
                conditionId = updateMonitor?.conditionId ?: return,
                strategyId = 1,
                monitorPrice = 350.0
            ).logResponse(TAG)
            updateMonitorPushNotification(
                conditionId = updateMonitor.conditionId ?: return,
                isNeedPush = true
            ).logResponse(TAG)
            delay(1000)
            val monitorListResult2 = getMonitorList()
            monitorListResult2.logResponse(TAG)
            val deleteMonitorList = monitorListResult2.getOrNull().orEmpty()
            delay(1000)
            deleteMonitorList.forEach {
                deleteMonitor(it.conditionId ?: 0)
            }
            val mrOptionConditionList = listOf(
                UpdateMrOptionConditionRequestBody.Condition(
                    1,
                    listOf(-1000, -500, 0, 500, 1000)
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    2,
                    listOf(-30, -15, 0, 15, 30)
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    3,
                    listOf(-30, -15, 0, 15, 30)
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    4,
                    listOf(-30, -15, 0, 15, 30)
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    5,
                    listOf(-30, -15, 0, 15, 30)
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    6,
                    listOf()
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    7,
                    listOf()
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    8,
                    listOf()
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    9,
                    listOf()
                ),
                UpdateMrOptionConditionRequestBody.Condition(
                    10,
                    listOf()
                )
            )
            updateMrOptionList(mrOptionConditionList)
                .logResponse(TAG)
            delay(1000)
            getMrOptionOptionList()
                .logResponse(TAG)
            getMrOptionSpotGoodsList()
                .logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "Notification2"
    }
}