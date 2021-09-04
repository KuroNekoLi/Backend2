package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.userbehavior.service.UserBehaviorWeb
import com.cmoney.backend2.userbehavior.service.api.common.Event
import org.koin.core.inject

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
                        descriptions = mapOf("Unit Test" to "Unit Test"),
                        name = "Unit Test",
                        time = System.currentTimeMillis()
                    )
                ),
                processId = "Unit Test",
                appId = -1,
                platform = -1,
                version = "Unit Test",
                os = "Unit Test",
                device = "Unit Test"
            ).logResponse(TAG)
        }
    }

    companion object {
        private val TAG = UserBehaviorServiceCase::class.java.simpleName
    }
}