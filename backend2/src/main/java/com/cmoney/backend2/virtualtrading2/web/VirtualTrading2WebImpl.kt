package com.cmoney.backend2.virtualtrading2.web

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountRequest
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountResponse
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class VirtualTrading2WebImpl(
    override val requestConfig: VirtualTradingRequestConfig,
    private val service: VirtualTrading2Service,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : VirtualTrading2Web {

    override suspend fun createAccount(
        domain: String,
        url: String,
        request: CreateAccountRequest
    ): Result<CreateAccountResponse> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = CreateAccountRequestBody(
                accountInvestType = request.accountInvestType.value,
                cardSn = request.cardSn
            )
            val response = service.createAccount(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
            CreateAccountResponse(
                accountId = response.accountId,
                name = response.name,
                groupId = response.groupId,
                memberId = response.memberId,
                defaultFunds = response.defaultFunds,
                funds = response.funds,
                isNeedFee = response.isNeedFee,
                isNeedTax = response.isNeedTax,
                canWatch = response.canWatch,
                isDefault = response.isDefault,
                isDelete = response.isDelete,
                accountType = response.accountType,
                createTime = response.createTime,
                updateTime = response.updateTime,
                viewTime = response.viewTime,
                accountPayType = response.accountPayType?.let { CreateAccountResponse.AccountPayType.valueOf(it)},
                maxReadSn = response.maxReadSn,
                isEmail = response.isEmail,
                averageTradingCountInMonth = response.averageTradingCountInMonth,
                totalPunishment = response.totalPunishment,
                tradedWarrantDate = response.tradedWarrantDate,
                extendFunds = response.extendFunds,
                stockIncomeLoss = response.stockIncomeLoss,
                warrantIncomeLoss = response.warrantIncomeLoss,
                futureIncomeLoss = response.futureIncomeLoss,
                optionIncomeLoss = response.optionIncomeLoss,
                borrowFunds = response.borrowFunds,
                borrowLimit = response.borrowLimit
            )
        }
    }
}