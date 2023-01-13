package com.cmoney.backend2.virtualtrading2.web

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountResponseBody
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
            val responseBody = service.createAccount(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
            CreateAccountResponse(
                accountId = responseBody.accountId,
                name = responseBody.name,
                groupId = responseBody.groupId,
                memberId = responseBody.memberId,
                defaultFunds = responseBody.defaultFunds,
                funds = responseBody.funds,
                isNeedFee = responseBody.isNeedFee,
                isNeedTax = responseBody.isNeedTax,
                canWatch = responseBody.canWatch,
                isDefault = responseBody.isDefault,
                isDelete = responseBody.isDelete,
                accountType = responseBody.accountType,
                createTime = responseBody.createTime,
                updateTime = responseBody.updateTime,
                viewTime = responseBody.viewTime,
                accountPayType = responseBody.accountPayType?.let {
                    CreateAccountResponse.AccountPayType.valueOf(
                        it
                    )
                },
                maxReadSn = responseBody.maxReadSn,
                isEmail = responseBody.isEmail,
                averageTradingCountInMonth = responseBody.averageTradingCountInMonth,
                totalPunishment = responseBody.totalPunishment,
                tradedWarrantDate = responseBody.tradedWarrantDate,
                extendFunds = responseBody.extendFunds,
                stockIncomeLoss = responseBody.stockIncomeLoss,
                warrantIncomeLoss = responseBody.warrantIncomeLoss,
                futureIncomeLoss = responseBody.futureIncomeLoss,
                optionIncomeLoss = responseBody.optionIncomeLoss,
                borrowFunds = responseBody.borrowFunds,
                borrowLimit = responseBody.borrowLimit
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
            val responseBody = service.createTseOtcDelegate(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
            CreateDelegateResponse(delegateId = responseBody.delegateId)
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

    override suspend fun getAccount(
        domain: String,
        url: String,
        query: String
    ): Result<GetAccountResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetAccountRequestBody(
                query = query
            )
            service.getAccount(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getAllAccount(
        domain: String,
        url: String,
        query: String
    ): Result<GetAllAccountResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetAllAccountRequestBody(
                query = query
            )
            service.getAllAccount(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }
}