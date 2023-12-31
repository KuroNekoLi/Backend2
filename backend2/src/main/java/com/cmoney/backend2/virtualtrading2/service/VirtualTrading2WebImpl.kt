package com.cmoney.backend2.virtualtrading2.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatebyid.GetDelegateByIdRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatebyid.GetDelegateByIdResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.gethistoryalldelegate.GetHistoryAllDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.gethistoryalldelegate.GetHistoryAllDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory.GetAllInventoryRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory.GetAllInventoryResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealbyid.GetSuccessDealByIdRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealbyid.GetSuccessDealByIdResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.gettodayalldelegate.GetTodayAllDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.gettodayalldelegate.GetTodayAllDelegateResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class VirtualTrading2WebImpl(
    override val globalBackend2Manager: GlobalBackend2Manager,
    private val service: VirtualTrading2Service,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
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
        delegatePrice: String,
        delegateVolume: String,
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcHistoryAllDelegate(
        domain: String,
        url: String,
        query: String
    ): Result<GetHistoryAllDelegateResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetHistoryAllDelegateRequestBody(
                query = query
            )
            service.getTseOtcHistoryAllDelegate(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcDelegateById(
        domain: String,
        url: String,
        query: String
    ): Result<GetDelegateByIdResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetDelegateByIdRequestBody(
                query = query
            )
            service.getTseOtcDelegateById(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcTodayAllDelegate(
        domain: String,
        url: String,
        query: String
    ): Result<GetTodayAllDelegateResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetTodayAllDelegateRequestBody(
                query = query
            )
            service.getTseOtcTodayAllDelegate(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcSuccessDealById(
        domain: String,
        url: String,
        query: String
    ): Result<GetSuccessDealByIdResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetSuccessDealByIdRequestBody(
                query = query
            )
            service.getTseOtcSuccessDealById(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTseOtcAllInventory(
        domain: String,
        url: String,
        query: String
    ): Result<GetAllInventoryResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = GetAllInventoryRequestBody(
                query = query
            )
            service.getTseOtcAllInventory(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }
}