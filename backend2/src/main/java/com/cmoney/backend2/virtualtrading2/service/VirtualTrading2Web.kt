package com.cmoney.backend2.virtualtrading2.service

import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccountratio.GetAccountRatioResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate.DeleteDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate.GetAllDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatedetail.GetDelegateDetailResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory.GetAllInventoryResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealdetail.GetSuccessDealDetailResponseBody

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
     * @param accountInvestType 投資帳戶類型 (現股 : 1 / 期權 : 2)
     * @param cardSn 道具卡序號，沒有道具卡填0(免費創建)
     *
     */
    suspend fun createAccount(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}account-api/Account",
        accountInvestType: Int,
        cardSn: Long
    ): Result<CreateAccountResponseBody>

    /**
     * 建立上市上櫃委託
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param accountId 帳號編號
     * @param buySellType 買賣類型，買單: 66 / 賣單: 83
     * @param commodityId 商品代號
     * @param subsistingType 存續種類
     * 立即成交否則取消單(IOC) 73 : 所下委託單必須馬上成交，否則即行取消之委託方式。
     * 立即全部成交否則作廢單(FOK) 70 : 所下委託單必須立即且全部成交，否則即行取消之委託方式。
     * 當日有效單(ROD) 82 : 所下委託單限當日有效。
     * @param groupId 競技場編號
     * @param delegatePrice 委託價
     * @param delegateVolume 委託量(股)
     * @param marketUnit 市場交易單位，整股: 1
     * @param transactionType 交易類型，現股: 1 / 融資: 2 / 融券: 3
     *
     */
    suspend fun createTseOtcDelegate(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/Trading/TseOtc/NewOrder",
        accountId: Long,
        buySellType: Int,
        commodityId: String,
        subsistingType: Int,
        groupId: Long,
        delegatePrice: String,
        delegateVolume: String,
        marketUnit: Int,
        transactionType: Int
    ): Result<CreateDelegateResponseBody>

    /**
     * 刪除上市上櫃委託
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param accountId 帳號編號
     * @param groupId 競技場編號
     * @param delegateId 委託單編號
     *
     */
    suspend fun deleteTseOtcDelegate(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/Trading/TseOtc/CancelOrder",
        accountId: Long,
        groupId: Long,
        delegateId: Long
    ): Result<DeleteDelegateResponseBody>

    /**
     * 取得帳號
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param query 查詢內容
    {
    accountInfo(accountId: $id) {
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
     */
    suspend fun getAccount(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}account-api/graphql",
        query: String
    ): Result<GetAccountResponseBody>

    /**
     * 取得所有帳號
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param query 查詢內容
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
     */
    suspend fun getAllAccount(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}account-api/graphql",
        query: String
    ): Result<GetAllAccountResponseBody>

    /**
     * 取得帳號報酬率
     *
    {
    accountRatios(accountId: $id, mkType: $type, dateCount: $count) {
    account
    dataDe
    funds
    inventoryValues
    isWeekend
    ratio
    }
    }
     */
    suspend fun getAccountRatio(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}account-api/graphql",
        query: String
    ): Result<GetAccountRatioResponseBody>

    /**
     * 取得上市櫃所有的委託單
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param query 查詢內容
    {
    tseOtcOrderByCustomPeriod(
    accountId: $id,
    beginTime: "yyyy/MM/dd",
    endTime: "yyyy/MM/dd",
    tradeType: $type，ALL : 0 / 現股: 1 / 融資: 2 / 融券: 3
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
     */
    suspend fun getTseOtcAllDelegate(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/graphql",
        query: String
    ): Result<GetAllDelegateResponseBody>

    /**
     * 取得上市櫃的委託單細節
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param query 查詢內容
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
     */
    suspend fun getTseOtcDelegateDetail(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/graphql",
        query: String
    ): Result<GetDelegateDetailResponseBody>

    /**
     * 取得上市櫃所有的成交單
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param query 查詢內容
    {
    tseOtcDealByCustomPeriod(
    accountId: $id,
    beginTime: "yyyy/MM/dd",
    endTime: "yyyy/MM/dd",
    tradeType: $type，ALL : 0 / 現股: 1 / 融資: 2 / 融券: 3
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
     */
    suspend fun getTseOtcAllSuccessDeal(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/graphql",
        query: String
    ): Result<GetAllSuccessDealResponseBody>

    /**
     * 取得上市櫃的成交單細節
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param query 查詢內容
    {
    tseOtcDeal(
    accountId: $accountId,
    orderNo: $delegateId
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
     */
    suspend fun getTseOtcSuccessDealDetail(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/graphql",
        query: String
    ): Result<GetSuccessDealDetailResponseBody>

    /**
     * 取得上市櫃的庫存
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param query 查詢內容
    {
    tseOtcPosition(accountId: $accountId) {
    account
    bs
    canOrdQty
    commKey
    commName
    cost
    createTime
    dealAvgPr
    incomeLoss
    incomeLossWithoutPreFee
    inventoryQty
    nowPr
    ratio
    shortSellingFee
    showCost
    taxCost
    todayInventoryQty
    tradeName
    tradeType
    }
    }
     */
    suspend fun getTseOtcAllInventory(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/graphql",
        query: String
    ): Result<GetAllInventoryResponseBody>
}