package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.portal.service.PortalWeb
import com.cmoney.backend2.portal.service.api.ForecastValue
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class PortalServiceCase : ServiceCase {

    private val cmPortalServiceImpl by inject<PortalWeb>()
    private val globalBackend2Manager by inject<GlobalBackend2Manager>()

    override suspend fun testAll() {
        with(cmPortalServiceImpl) {
            getTarget().logResponse(TAG)
            getSignals().logResponse(TAG)
            getAdditionalInfo(3).logResponse(TAG)
            //猜多空系列api
            val result = getActivitiesBaseInfo()
            val guessBullBearCommKey =
                result.getOrNull()?.activityBaseInfoList?.firstOrNull()?.commKey ?: "TWA00"
            result.logResponse(TAG)
            getActivityNowInfo(guessBullBearCommKey)
                .logResponse(TAG)
            getMemberPerformance(guessBullBearCommKey, globalBackend2Manager.getIdentityToken().getMemberGuid())
                .logResponse(TAG)
            getRanking(guessBullBearCommKey, 30, 0)
                .logResponse(TAG)
            getPersonActivityHistory(
                guessBullBearCommKey,
                30,
                0,
                globalBackend2Manager.getIdentityToken().getMemberGuid()
            )
                .logResponse(TAG)
            askMemberForecastStatus(guessBullBearCommKey)
                .logResponse(TAG)
            askMemberLastForecastInfo(guessBullBearCommKey)
                .logResponse(TAG)
            joinActivity(guessBullBearCommKey, ForecastValue.Bull)
                .logResponse(TAG)
            askAllMemberLastForecastInfo()
                .logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "PortalServiceCase"
    }
}