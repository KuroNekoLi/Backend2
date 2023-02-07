package com.cmoney.backend2.vtwebapi.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.setting.Setting
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

/**
 * @property servicePath 服務路徑
 */
class VirtualTradeWebImpl(
    override val setting: Setting,
    private val service: VirtualTradeService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : VirtualTradeWeb {

    private val servicePath: String = "vt.webapi"

    override suspend fun getAccount(
        domain: String,
        destMemberPk: Long?,
        skipCount: Int?,
        fetchSize: Int?,
        needGroupAccount: Boolean?,
        needExtendInfo: Boolean?
    ): Result<List<GetAccountResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAccount(
                url = "$domain$servicePath/Account",
                authorization = setting.accessToken.createAuthorizationBearer(),
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
        type: AccountType,
        isn: Long
    ): Result<GetAccountResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.createAccount(
                url = "$domain$servicePath/Account",
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = CreateAccountRequestBody(
                    type = type.typeNum,
                    isn = isn
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getCardInstanceSns(
        domain: String,
        productSn: Long,
        productUsage: UsageType
    ): Result<GetCardInstanceSnsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCardInstanceSns(
                url = "$domain$servicePath/ByProductSn",
                authorization = setting.accessToken.createAuthorizationBearer(),
                productSn = productSn,
                productUsage = productUsage.typeNum,
            ).checkResponseBody(gson)
        }
    }

    override suspend fun purchaseProductCard(
        domain: String,
        giftFromMember: Int,
        ownerMemberPk: Int,
        productSn: Long
    ): Result<PurchaseProductCardResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.purchaseProductCard(
                url = "$domain$servicePath/ProductCard/PurchaseProductCard",
                authorization = setting.accessToken.createAuthorizationBearer(),
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
        fetchIndex: Int?,
        fetchSize: Int?
    ): Result<List<GetAttendGroupResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAttendGroup(
                url = "$domain$servicePath/Group/Attend",
                authorization = setting.accessToken.createAuthorizationBearer(),
                fetchIndex = fetchIndex,
                fetchSize = fetchSize
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getStockInventoryList(
        domain: String,
        account: Long
    ): Result<List<GetStockInventoryListResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getStockInventoryList(
                url = "$domain$servicePath/Inventory/SecInventoryListByAccount/$account",
                authorization = setting.accessToken.createAuthorizationBearer()
            ).checkResponseBody(gson)
        }
    }
}