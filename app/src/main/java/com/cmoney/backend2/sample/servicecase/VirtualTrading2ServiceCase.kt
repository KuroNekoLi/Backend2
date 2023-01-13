package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.virtualtrading2.web.VirtualTrading2Web
import com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate.CreateDelegateRequest
import com.cmoney.backend2.virtualtrading2.web.tseotc.deletedelegate.DeleteDelegateRequest
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
        val createDelegate = web.createTseOtcDelegate(
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
        )
        createDelegate.logResponse(TAG)
        createDelegate.onSuccess { response ->
            val delegateId = response.delegateId ?: return@onSuccess
            web.deleteTseOtcDelegate(
                request = DeleteDelegateRequest(
                    accountId = accountId,
                    groupId = 0,
                    delegateId = delegateId,
                )
            ).logResponse(TAG)
        }
        web.getAllAccount(
            query = """
              {
                allAccountInfo {
                    account
                    accountPayType
                    accountType
                    avgMonthOrderCount
                    borrowFunds
                    borrowLimit
                    canWatch
                    createTime
                    defaultFunds
                    extendFunds
                    funds
                    groupId
                    isDefault
                    isDelete
                    isEmail
                    maxReadSn
                    memberId
                    name
                    needFee
                    needTax
                    optIncomeLoss
                    stockIncomeLoss
                    tmxIncomeLoss
                    totalPunishment
                    tradedWarrantDate
                    updateTime
                    viewTime
                    warrantIncomeLoss
                }
            }
        """.trimIndent()
        ).logResponse(TAG)

        web.getAccount(
            query = """
            {
              accountInfo(accountId: ${accountId}) {
                account
                accountPayType
                accountType
                avgMonthOrderCount
                borrowFunds
                borrowLimit
                canWatch
                createTime
                defaultFunds
                extendFunds
                funds
                groupId
                isDefault
                isEmail
                maxReadSn
                memberId
                name
                needFee
                needTax
                optIncomeLoss
                stockIncomeLoss
                tmxIncomeLoss
                totalPunishment
                tradedWarrantDate
                updateTime
                viewTime
                warrantIncomeLoss
              }
            }
        """.trimIndent()
        ).logResponse(TAG)

        web.getAccountRatio(
            query = """
          {
              accountRatios(accountId: $accountId, mkType: 1, dateCount: 360) {
                account
                dataDe
                funds
                inventoryValues
                isWeekend
                ratio
              }
            }
        """.trimIndent()
        ).logResponse(TAG)
    }

    companion object {
        private const val TAG = "VirtualTrading2"
    }
}