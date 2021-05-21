package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.notification2.service.Notification2Web
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.PushSetting
import com.cmoney.backend2.notification2.service.api.updatemroptionlist.UpdateMrOptionConditionRequestBody
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.sample.servicecase.data.Notification2TestParameter
import kotlinx.coroutines.delay
import org.koin.core.inject

/**
 * 推播開關服務測試
 *
 */
class Notification2ServiceCase : ServiceCase {

    private val userSettingImpl by inject<Notification2Web>()

    override suspend fun testAll() {
        userSettingImpl.apply {
            getHistoryNotifyAll(Notification2TestParameter::class.java).logResponse("TEST_FROM_SPINNER")

            getBranchFcm()
            //推播設定ID
            val pushSettingId = 6
            updateBranchFcm(pushSettingId, true)
            //多組推播設定
            val pushSettings = listOf(
                PushSetting(true, 17),
                PushSetting(true, 18),
                PushSetting(true, 19)
            )
            updateBranchFcmMultipleSettings(pushSettings)
            val clubId = 105551L
            getClubFcm(clubId)
            val randType = (0..2).random()
            updateClubFcm(randType, clubId)
            getMainFcm()
            updateMainFcm(true)
            //Monitor
            delay(5000)
            val commonKey = "2330"
            getMonitorList()
            delay(1000)
            insertMonitor(commonKey, 1, 400.0)
            delay(1000)
            val updateMonitorList = getMonitorList().getOrNull().orEmpty()
            delay(1000)
            val updateMonitor = updateMonitorList.find {
                it.commonKey.orEmpty() == commonKey
            }
            updateMonitor(
                conditionId = updateMonitor?.conditionId ?: return,
                strategyId = 1,
                monitorPrice = 350.0
            )
            updateMonitorPushNotification(
                conditionId = updateMonitor.conditionId ?: return,
                isNeedPush = true
            )
            delay(1000)
            val deleteMonitorList = getMonitorList().getOrNull().orEmpty()
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
            delay(1000)
            getMrOptionOptionList()
            getMrOptionSpotGoodsList()
        }
    }

    companion object {
        private val TAG = Notification2ServiceCase::class.java.simpleName
    }
}