package com.cmoney.backend2.virtualtrading2.web

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate.DeleteDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountRequest
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountResponse
import com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate.CreateDelegateRequest
import com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate.CreateDelegateResponse
import com.cmoney.backend2.virtualtrading2.web.tseotc.deletedelegate.DeleteDelegateRequest
import com.cmoney.backend2.virtualtrading2.web.tseotc.deletedelegate.DeleteDelegateResponse
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
                accountPayType = response.accountPayType?.let {
                    CreateAccountResponse.AccountPayType.valueOf(
                        it
                    )
                },
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

    override suspend fun createTseOtcDelegate(
        domain: String,
        url: String,
        request: CreateDelegateRequest
    ): Result<CreateDelegateResponse> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = CreateDelegateRequestBody(
                accountId = request.accountId,
                buySellType = request.buySellType.code,
                commodityId = request.commodityId,
                subsistingType = request.subsistingType.code,
                groupId = request.groupId,
                delegatePrice = request.delegatePrice.toDouble(),
                delegateVolume = request.delegateVolume.toLong(),
                marketUnit = request.marketUnit.code,
                transactionType = request.transactionType.code
            )
            val response = service.createTseOtcDelegate(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
            CreateDelegateResponse(delegateId = response.delegateId)
        }
    }

    override suspend fun deleteTseOtcDelegate(
        domain: String,
        url: String,
        request: DeleteDelegateRequest
    ): Result<DeleteDelegateResponse> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = DeleteDelegateRequestBody(
                accountId = request.accountId,
                groupId = request.groupId,
                delegateId = request.delegateId
            )
            val response = service.deleteTseOtcDelegate(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
            DeleteDelegateResponse(delegateId = response.delegateId)
        }
    }
}