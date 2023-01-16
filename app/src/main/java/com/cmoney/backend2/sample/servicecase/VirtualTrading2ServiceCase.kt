package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Web
import org.koin.core.component.inject

class VirtualTrading2ServiceCase : ServiceCase {

    private val web by inject<VirtualTrading2Web>()

    override suspend fun testAll() {
        // 目前還沒有刪除帳號功能，並且一個CMoney帳號只能建立3個
        web.createAccount(
            accountInvestType = 1,
            cardSn = 0
        ).logResponse(TAG)
        val accountId = 2076L
        val createDelegate = web.createTseOtcDelegate(
            accountId = accountId,
            buySellType = 66,
            commodityId = "2890",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = 17.2,
            delegateVolume = 1000,
            marketUnit = 1,
            transactionType = 1
        )
        createDelegate.logResponse(TAG)
        createDelegate.onSuccess { response ->
            val delegateId = response.delegateId ?: return@onSuccess
            web.deleteTseOtcDelegate(
                accountId = accountId,
                groupId = 0,
                delegateId = delegateId
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
        val allDelegate = web.getTseOtcAllDelegate(
            query = """
                    {
                    tseOtcOrderByCustomPeriod(
                    accountId: $accountId,
                    beginTime: "2023/01/01",
                    endTime: "2023/12/31",
                    tradeType: 1
                    ) {
                    ordNo
                    targetOrdNo
                    account
                    groupId
                    tradeTime
                    status
                    ordType
                    condition
                    tradeType
                    stockMarketType
                    buySellType
                    commKey
                    ordPr
                    ordQty
                    dealAvgPr
                    dealQty
                    avQty
                    cutQty
                    prePayment
                    serverRcvTe
                    serverRcvNo
                    marginCredit
                    marginOwn
                    shortSellingCollateral
                    shortSellingEntrust
                    memo
                    noteId
                    modifyTime
                    }
                    }
        """.trimIndent()
        )
        allDelegate.logResponse(TAG)
        val delegateId = allDelegate.getOrNull()?.content?.delegateList?.firstOrNull()?.delegateId
        web.getTseOtcDelegateDetail(
            query = """
                    {
                    tseOtcOrder(accountId: $accountId, orderNo: $delegateId) {    
                    ordNo
                    targetOrdNo
                    account
                    groupId
                    tradeTime
                    status
                    ordType
                    condition
                    tradeType
                    stockMarketType
                    buySellType
                    commKey
                    ordPr
                    ordQty
                    dealAvgPr
                    dealQty
                    avQty
                    cutQty
                    prePayment
                    serverRcvTe
                    serverRcvNo
                    marginCredit
                    marginOwn
                    shortSellingCollateral
                    shortSellingEntrust
                    memo
                    noteId
                    modifyTime
                    }
                    }
        """.trimIndent()
        ).logResponse(TAG)
        val allSuccessDeal = web.getTseOtcAllSuccessDeal(
            query = """
                {
                    tseOtcDealByCustomPeriod(
                        accountId: $accountId,
                        beginTime: "2023/01/01",
                        endTime: "2023/12/31",
                        tradeType: 1
                    ) {
                        te
                        account
                        ordNo
                        stockMarketType
                        tradeType
                        buySellType
                        commKey
                        dealPr
                        dealQty
                        fee
                        tax
                        dealTno
                        flag
                        sn
                        shortSellingFee
                        memo
                        actualCost
                        borrow
                        bsAvgPr
                        remainQty
                        isSuccess
                    }
                }
        """.trimIndent()
        ).logResponse(TAG)
    }

    companion object {
        private const val TAG = "VirtualTrading2"
    }
}