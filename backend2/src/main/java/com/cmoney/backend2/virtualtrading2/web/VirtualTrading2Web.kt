package com.cmoney.backend2.virtualtrading2.web

import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountRequest
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountResponse

interface VirtualTrading2Web {

    /**
     * 虛擬交易請求設定
     */
    val requestConfig: VirtualTradingRequestConfig

    /**
     * 建立帳號
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param request 請求內容
     *
     */
    suspend fun createAccount(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}account-api/Account",
        request: CreateAccountRequest
    ): Result<CreateAccountResponse>
}