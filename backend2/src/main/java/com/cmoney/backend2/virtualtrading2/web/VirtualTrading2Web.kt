package com.cmoney.backend2.virtualtrading2.web

import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountResponseBody
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountRequest
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountResponse
import com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate.CreateDelegateRequest
import com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate.CreateDelegateResponse
import com.cmoney.backend2.virtualtrading2.web.tseotc.deletedelegate.DeleteDelegateRequest
import com.cmoney.backend2.virtualtrading2.web.tseotc.deletedelegate.DeleteDelegateResponse

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

    /**
     * 建立上市上櫃委託
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param request 請求內容
     *
     */
    suspend fun createTseOtcDelegate(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/Trading/TseOtc/NewOrder",
        request: CreateDelegateRequest
    ): Result<CreateDelegateResponse>

    /**
     * 刪除上市上櫃委託
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param request 請求內容
     *
     */
    suspend fun deleteTseOtcDelegate(
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}trading-api/Trading/TseOtc/CancelOrder",
        request: DeleteDelegateRequest
    ): Result<DeleteDelegateResponse>

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
}