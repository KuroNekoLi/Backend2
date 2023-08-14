package com.cmoney.backend2.crawlsetting.model

/**
 * 爬蟲服務設定轉接器
 */
interface CrawlSettingSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}