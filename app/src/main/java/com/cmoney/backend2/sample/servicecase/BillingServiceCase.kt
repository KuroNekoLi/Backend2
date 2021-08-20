package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.billing.service.BillingWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class BillingServiceCase : ServiceCase {

    private val billingImpl by inject<BillingWeb>()

    override suspend fun testAll() {
        billingImpl.isReadyToPurchase()
            .logResponse(TAG)
        billingImpl.getProductInfo()
            .logResponse(TAG)
        billingImpl.getAuthStatus()
            .logResponse(TAG)
        billingImpl.getTargetAppAuthStatus(2)
            .logResponse(TAG)
        billingImpl.getAuthByCMoney(2)
            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "Billing"
    }
}
