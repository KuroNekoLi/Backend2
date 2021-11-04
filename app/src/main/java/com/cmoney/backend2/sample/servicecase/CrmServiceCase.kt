package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.crm.service.CrmWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class CrmServiceCase : ServiceCase {

    private val web by inject<CrmWeb>()

    override suspend fun testAll() {
        web.createLiveChat(isPro = true).logResponse(TAG)
        web.createLiveChat(isPro = false).logResponse(TAG)
    }

    companion object {
        private const val TAG = "Crm"
    }
}