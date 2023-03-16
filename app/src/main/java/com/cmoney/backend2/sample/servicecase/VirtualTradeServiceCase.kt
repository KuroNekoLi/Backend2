package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.vtwebapi.service.VirtualTradeWeb
import com.cmoney.backend2.vtwebapi.service.api.createaccount.AccountType
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.UsageType
import org.koin.core.component.inject

class VirtualTradeServiceCase : ServiceCase {

    private val web by inject<VirtualTradeWeb>()
    private val globalBackend2Manager by inject<GlobalBackend2Manager>()

    override suspend fun testAll() {
        web.getAccount(
            domain = globalBackend2Manager.getGlobalDomainUrl()
        )
            .logResponse(TAG)

        web.createAccount(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            type = AccountType.STOCK,
            isn = 0
        )
            .logResponse(TAG)

        web.getCardInstanceSns(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            productSn = TEST_PRODUCT,
            productUsage = UsageType.UNUSED
        )
            .logResponse(TAG)

        web.purchaseProductCard(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            giftFromMember = 22478,
            ownerMemberPk = 22478,
            productSn = TEST_PRODUCT
        )
            .logResponse(TAG)

        web.getAttendGroup(
            domain = globalBackend2Manager.getGlobalDomainUrl()
        )
            .logResponse(TAG)

        web.getStockInventoryList(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            account = TEST_ACCOUNT
        )
    }

    companion object {
        private const val TAG = "VirtualTrade"
        private const val TEST_ACCOUNT = 1970L
        private const val TEST_PRODUCT = 20L
    }
}