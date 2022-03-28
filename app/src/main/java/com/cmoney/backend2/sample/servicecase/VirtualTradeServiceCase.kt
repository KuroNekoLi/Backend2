package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.vtwebapi.service.VirtualTradeWeb
import com.cmoney.backend2.vtwebapi.service.api.createaccount.AccountType
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.UsageType
import org.koin.core.component.inject

class VirtualTradeServiceCase : ServiceCase {

    private val web by inject<VirtualTradeWeb>()
    private val setting by inject<Setting>(BACKEND2_SETTING)

    override suspend fun testAll() {
        web.getAccount(
            domain = setting.domainUrl
        )
            .logResponse(TAG)

        web.createAccount(
            domain = setting.domainUrl,
            type = AccountType.STOCK,
            isn = 0
        )
            .logResponse(TAG)

        web.getCardInstanceSns(
            domain = setting.domainUrl,
            productSn = TEST_PRODUCT,
            productUsage = UsageType.UNUSED
        )
            .logResponse(TAG)

        web.purchaseProductCard(
            domain = setting.domainUrl,
            giftFromMember = 22478,
            ownerMemberPk = 22478,
            productSn = TEST_PRODUCT
        )
            .logResponse(TAG)

        web.getAttendGroup(
            domain = setting.domainUrl
        )
            .logResponse(TAG)

        web.getStockInventoryList(
            domain = setting.domainUrl,
            account = TEST_ACCOUNT
        )
    }

    companion object {
        private const val TAG = "VirtualTrade"
        private const val TEST_ACCOUNT = 1970L
        private const val TEST_PRODUCT = 20L
    }
}