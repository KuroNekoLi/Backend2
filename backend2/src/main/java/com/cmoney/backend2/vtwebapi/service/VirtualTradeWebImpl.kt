package com.cmoney.backend2.vtwebapi.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.vtwebapi.service.api.createaccount.AccountType
import com.cmoney.backend2.vtwebapi.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.vtwebapi.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getattendgroup.GetAttendGroupResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.GetCardInstanceSnsResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.UsageType
import com.cmoney.backend2.vtwebapi.service.api.getstockinventorylist.GetStockInventoryListResponseBody
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardRequestBody
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class VirtualTradeWebImpl(
    override val globalBackend2Manager: GlobalBackend2Manager,
    private val service: VirtualTradeService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : VirtualTradeWeb {

    override suspend fun getAccount(
        domain: String,
        url: String,
        destMemberPk: Long?,
        skipCount: Int?,
        fetchSize: Int?,
        needGroupAccount: Boolean?,
        needExtendInfo: Boolean?
    ): Result<List<GetAccountResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAccount(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                destMemberPk = destMemberPk,
                skipCount = skipCount,
                fetchSize = fetchSize,
                needGroupAccount = needGroupAccount,
                needExtendInfo = needExtendInfo
            ).checkResponseBody(gson)
        }
    }

    override suspend fun createAccount(
        domain: String,
        url: String,
        type: AccountType,
        isn: Long
    ): Result<GetAccountResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.createAccount(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = CreateAccountRequestBody(
                    type = type.typeNum,
                    isn = isn
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getCardInstanceSns(
        domain: String,
        url: String,
        productSn: Long,
        productUsage: UsageType
    ): Result<GetCardInstanceSnsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCardInstanceSns(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                productSn = productSn,
                productUsage = productUsage.typeNum,
            ).checkResponseBody(gson)
        }
    }

    override suspend fun purchaseProductCard(
        domain: String,
        url: String,
        giftFromMember: Int,
        ownerMemberPk: Int,
        productSn: Long
    ): Result<PurchaseProductCardResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.purchaseProductCard(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = PurchaseProductCardRequestBody(
                    giftFromMember = giftFromMember,
                    ownerMemberPk = ownerMemberPk,
                    productSn = productSn
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getAttendGroup(
        domain: String,
        url: String,
        fetchIndex: Int?,
        fetchSize: Int?
    ): Result<List<GetAttendGroupResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAttendGroup(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                fetchIndex = fetchIndex,
                fetchSize = fetchSize
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getStockInventoryList(
        account: Long,
        domain: String,
        url: String
    ): Result<List<GetStockInventoryListResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getStockInventoryList(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(gson)
        }
    }
}