package com.cmoney.backend2.crawlsetting.model

class CrawlSettingSettingAdapterImpl : CrawlSettingSettingAdapter {
    override fun getDomain(): String {
        return "https://datateamapi.cmoney.tw/"
    }
}
