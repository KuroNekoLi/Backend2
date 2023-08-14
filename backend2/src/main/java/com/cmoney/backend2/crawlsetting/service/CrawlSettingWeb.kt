package com.cmoney.backend2.crawlsetting.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager

/**
 * 庫存爬蟲服務
 */
interface CrawlSettingWeb {

    val manager: GlobalBackend2Manager

    /**
     * 由國泰用戶的 [userInfoKey] 取得 CAStatus
     *
     * @param userInfoKey 國泰用戶的 userInfoKey
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getCathayCaStatus(
        userInfoKey: String,
        domain: String = manager.getCrawlSettingSettingAdapter().getDomain(),
        url: String = "${domain}CrawlSettingService/cathaycastatus"
    ): Result<String>

    /**
     * 由台新用戶的 [userInfoKey] 取得 CAStatus
     *
     * @param userInfoKey 台新用戶的 userInfoKey
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTaishinCaStatus(
        userInfoKey: String,
        domain: String = manager.getCrawlSettingSettingAdapter().getDomain(),
        url: String = "${domain}CrawlSettingService/taishincastatus"
    ): Result<String>

}
