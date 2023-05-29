package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.billing.service.BillingWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class BillingServiceCase : ServiceCase {

    private val billingImpl by inject<BillingWeb>()
    private val manager by inject<GlobalBackend2Manager>()

    override suspend fun testAll() {
        billingImpl.getDeveloperPayload()
            .logResponse(TAG)
        billingImpl.isReadyToPurchase()
            .logResponse(TAG)
        billingImpl.getProductInfo()
            .logResponse(TAG)
        billingImpl.getAuthStatus()
            .logResponse(TAG)
        billingImpl.getTargetAppAuthStatus(manager.getAppId())
            .logResponse(TAG)
        billingImpl.getAuthByCMoney(manager.getAppId())
            .logResponse(TAG)
        billingImpl.getHistoryCount(888003,6531)
            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "Billing"
    }
}
