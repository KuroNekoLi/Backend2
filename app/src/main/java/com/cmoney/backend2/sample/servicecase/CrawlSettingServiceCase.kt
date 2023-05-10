package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.crawlsetting.service.CrawlSettingWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class CrawlSettingServiceCase() : ServiceCase {

    private val web by inject<CrawlSettingWeb>()

    override suspend fun testAll() {
        web.getCathayCaStatus(
            userInfoKey = "B7828223EEAC33D284AF092851564BB61D598352E54E666DC1B5F6100BFD0438"
        ).logResponse(TAG)

        web.getTaishinCaStatus(
            userInfoKey = "27214C83DF796BD61C18409D9C91094D9D2E4125B1DE2C2B0D217409602F5EF8"
        ).logResponse(TAG)
    }

    companion object {
        private const val TAG = "CrawlSetting"
    }
}
