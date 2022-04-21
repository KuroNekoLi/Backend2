package com.cmoney.backend2.crawlsetting.service

/**
 * 庫存爬蟲服務
 *
 * @property baseHost url的host
 */
interface CrawlSettingWeb {

    val baseHost: String

    /**
     * 由國泰用戶的 [userInfoKey] 取得 CAStatus
     */
    suspend fun getCathayCaStatus(
        userInfoKey: String,
        host: String = baseHost
    ): Result<String>

    /**
     * 由台新用戶的 [userInfoKey] 取得 CAStatus
     */
    suspend fun getTaishinCaStatus(
        userInfoKey: String,
        host: String = baseHost
    ): Result<String>

}
