package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.crawlsetting.di.BACKEND2_CRAWL_SETTING_DEBUG
import com.cmoney.backend2.crawlsetting.service.CrawlSettingWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject
import org.koin.core.qualifier.StringQualifier

class CrawlSettingServiceCase(
    qualifier: StringQualifier = BACKEND2_CRAWL_SETTING_DEBUG
) : ServiceCase {

    private val web by inject<CrawlSettingWeb>(qualifier)

    override suspend fun testAll() {
        val result = web.getCathayCaStatus(
            userInfoKey = "B7828223EEAC33D284AF092851564BB61D598352E54E666DC1B5F6100BFD0438"
        )
        result.logResponse(TAG)
    }

    companion object {
        private const val TAG = "CrawlSetting"
    }
}
