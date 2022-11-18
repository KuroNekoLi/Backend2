package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.userbehavior.service.UserBehaviorWeb
import com.cmoney.backend2.userbehavior.service.api.common.Event
import org.koin.core.component.inject

/**
 * 推播開關服務測試
 *
 */
class UserBehaviorServiceCase : ServiceCase {

    private val web by inject<UserBehaviorWeb>()

    override suspend fun testAll() {
        web.apply {
            uploadReport(
                events = listOf(
                    Event(
                        descriptions = null,
                        name = "Test Event Key Without Info",
                        time = System.currentTimeMillis(),
                        duration = null
                    ),
                    Event(
                        descriptions = mapOf("I Should not be logged" to "我不應該被紀錄，因為我沒有被定義在Info"),
                        name = "Test Event Key With Error Info",
                        time = System.currentTimeMillis(),
                        duration = null
                    ),
                    Event(
                        descriptions = mapOf("test" to true),
                        name = "Test Event Key With Info",
                        time = System.currentTimeMillis(),
                        duration = 0
                    )
                ),
                processId = null,
                appId = 2,
                platform = 2,
                version = "Unit Test",
                os = null,
                device = null
            ).logResponse(TAG)
        }
    }

    companion object {
        private val TAG = UserBehaviorServiceCase::class.java.simpleName
    }
}