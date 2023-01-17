package com.cmoney.backend2.virtualtrading2.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccountratio.GetAccountRatioRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccountratio.GetAccountRatioResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate.DeleteDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate.DeleteDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate.GetAllDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate.GetAllDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatedetail.GetDelegateDetailRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatedetail.GetDelegateDetailResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealdetail.GetSuccessDealDetailRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealdetail.GetSuccessDealDetailResponseBody
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
        accountInvestType: Int,
        cardSn: Long
    ): Result<CreateAccountResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = CreateAccountRequestBody(
                accountInvestType = accountInvestType,
                cardSn = cardSn
            )
            service.createAccount(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun createTseOtcDelegate(
        domain: String,
        url: String,
        accountId: Long,
        buySellType: Int,
        commodityId: String,
        subsistingType: Int,
        groupId: Long,
        delegatePrice: Double,
        delegateVolume: Long,
        marketUnit: Int,
        transactionType: Int
    ): Result<CreateDelegateResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = CreateDelegateRequestBody(
                accountId = accountId,
                buySellType = buySellType,
                commodityId = commodityId,
                subsistingType = subsistingType,
                groupId = groupId,
                delegatePrice = delegatePrice,
                delegateVolume = delegateVolume,
                marketUnit = marketUnit,
                transactionType = transactionType
            )
            service.createTseOtcDelegate(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun deleteTseOtcDelegate(
        domain: String,
        url: String,
        accountId: Long,
        groupId: Long,
        delegateId: Long
    ): Result<DeleteDelegateResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = DeleteDelegateRequestBody(
                accountId = accountId,
                groupId = groupId,
                delegateId = delegateId
            )
            service.deleteTseOtcDelegate(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
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

    override suspend fun getAccountRatio(
        domain: String,
        url: String,
        query: String
    ): Result<GetAccountRatioResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetAccountRatioRequestBody(
                query = query
            )
            service.getAccountRatio(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcAllDelegate(
        domain: String,
        url: String,
        query: String
    ): Result<GetAllDelegateResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetAllDelegateRequestBody(
                query = query
            )
            service.getTseOtcAllDelegate(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcDelegateDetail(
        domain: String,
        url: String,
        query: String
    ): Result<GetDelegateDetailResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetDelegateDetailRequestBody(
                query = query
            )
            service.getTseOtcDelegateDetail(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcAllSuccessDeal(
        domain: String,
        url: String,
        query: String
    ): Result<GetAllSuccessDealResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetAllSuccessDealRequestBody(
                query = query
            )
            service.getTseOtcAllSuccessDeal(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcSuccessDealDetail(
        domain: String,
        url: String,
        query: String
    ): Result<GetSuccessDealDetailResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetSuccessDealDetailRequestBody(
                query = query
            )
            service.getTseOtcSuccessDealDetail(
                url = url,
                authorization = requestConfig.getBearerToken(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }
}