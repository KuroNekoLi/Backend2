package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.trial.service.TrialWeb
import org.koin.core.component.inject

/**
 * 推播開關服務測試
 *
 */
class TrialServiceCase : ServiceCase {

    private val trial2Web by inject<TrialWeb>()

    override suspend fun testAll() {
        trial2Web.apply {
            setQuotaUsageUse(QUOTA_KEY).logResponse(TAG)
            setQuotaTimeUse(QUOTA_KEY, 20).logResponse(TAG)
            checkTrialTime(TIME_KEY).logResponse(TAG)
            getQuotaTime(QUOTA_KEY).logResponse(TAG)
        }
    }

    companion object {
        private val TAG = TrialServiceCase::class.java.simpleName

        /**
         * 金鑰描述
         * {
         * "appId": 4,
         * "name": "quota測試用",
         * "trialKey": "221604d5-1af8-412e-b521-0c6ceaaf385d",
         * "trialType": 1,
         * "quota": 100,
         * "createTime": "2020-10-20T16:05:01.107"
         * }
         */
        private const val QUOTA_KEY = "221604d5-1af8-412e-b521-0c6ceaaf385d"

        /**
         * 金鑰描述
         * {
         * "appId": 4,
         * "name": "time測試用",
         * "trialKey": "8ebe725f-7aaa-4ca6-b189-b55a1abd4233",
         * "trialType": 2,
         * "quota": 100,
         * "createTime": "2020-10-20T16:05:52.46"
         * }
         */
        private const val TIME_KEY = "8ebe725f-7aaa-4ca6-b189-b55a1abd4233"
    }
}