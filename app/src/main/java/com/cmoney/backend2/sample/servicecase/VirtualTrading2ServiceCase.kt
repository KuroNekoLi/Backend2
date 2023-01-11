package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.virtualtrading2.web.VirtualTrading2Web
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountRequest
import org.koin.core.component.inject

class VirtualTrading2ServiceCase : ServiceCase {

    private val web by inject<VirtualTrading2Web>()

    override suspend fun testAll() {
        // 目前還沒有刪除帳號功能，並且一個CMoney帳號只能建立3個
//        web.createAccount(
//            request = CreateAccountRequest(
//                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
//            )
//        ).logResponse(TAG)

    }

    companion object {
        private const val TAG = "VirtualTrading2"
    }
}