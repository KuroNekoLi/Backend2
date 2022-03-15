package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.vtwebapi.service.VirtualTradeWeb
import org.koin.core.component.inject

class VirtualTradeServiceCase : ServiceCase {

    private val web by inject<VirtualTradeWeb>()
    private val setting by inject<Setting>(BACKEND2_SETTING)

    override suspend fun testAll() {
        web.getAccount(
            domain = setting.domainUrl,
            destMemberPk = null,
            skipCount = null,
            fetchSize = null,
            needGroupAccount = null,
            needExtendInfo = null,
        )
            .logResponse(TAG)

        web.createAccount(
            domain = setting.domainUrl,
            type = 1,
            isn = 0
        )
            .logResponse(TAG)

        web.getCardInstanceSns(
            domain = setting.domainUrl,
            productSn = TEST_PRODUCT
        )
            .logResponse(TAG)

        web.purchaseProductCard(
            domain = setting.domainUrl,
            giftFromMember = 22478,
            ownerMemberPk = 2000,
            productSn = TEST_PRODUCT
        )
            .logResponse(TAG)

        web.getAttendGroup(
            domain = setting.domainUrl,
            fetchIndex = null,
            fetchSize = null
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