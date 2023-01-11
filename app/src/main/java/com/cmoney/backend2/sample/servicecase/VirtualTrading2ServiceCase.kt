package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.virtualtrading2.web.VirtualTrading2Web
import com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate.CreateDelegateRequest
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
        // TODO 取得帳號資訊
        val accountId = 2076L
        web.createTseOtcDelegate(
            request = CreateDelegateRequest(
                accountId = accountId,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "2890",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "17.2".toBigDecimal(),
                delegateVolume = "1000".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
        ).logResponse(TAG)
    }

    companion object {
        private const val TAG = "VirtualTrading2"
    }
}