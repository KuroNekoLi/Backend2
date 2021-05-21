package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.activity.service.ActivityWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class ActivityServiceCase : ServiceCase {
    private val activityWeb by inject<ActivityWeb>()

    override suspend fun testAll() {
        activityWeb.apply {
            getDayCount().logResponse(TAG)
            requestBonus(123504,7).logResponse(TAG)
            getReferralCount(7).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "Activity"
    }
}
