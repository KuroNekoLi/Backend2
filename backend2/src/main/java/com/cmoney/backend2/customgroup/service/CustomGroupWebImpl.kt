package com.cmoney.backend2.customgroup.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.customgroup.service.api.addcustomgroup.NewCustomGroup
import com.cmoney.backend2.customgroup.service.api.common.CustomGroupType
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder.SingleGroupWithOrder
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist.CustomGroupWithOrderAndList
import com.cmoney.backend2.customgroup.service.api.searchstocks.SearchStocksRequestBody
import com.cmoney.backend2.customgroup.service.api.searchstocks.SearchStocksResponseBody
import com.cmoney.backend2.customgroup.service.api.updatecustomgrouporder.UpdateCustomGroupOrderComplete
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class CustomGroupWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: CustomGroupService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : CustomGroupWeb {

    override suspend fun getCustomGroupListIncludeOrder(
        groupType: CustomGroupType,
        domain: String,
        url: String
    ): Result<List<SingleGroupWithOrder>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getCustomGroupWithOrder(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId(),
                    docType = groupType.name
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
                    .group
                    .orEmpty()
            }
        }

    override suspend fun getCustomGroupContent(
        docNo: Int,
        domain: String,
        url: String
    ): Result<List<String>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getCustomGroupContent(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                guid = manager.getIdentityToken().getMemberGuid(),
                appId = manager.getAppId(),
                docNo = docNo
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
                .stocks
                .orEmpty()
        }
    }

    override suspend fun getCustomGroupListIncludeOrderAndContent(
        groupType: CustomGroupType,
        domain: String,
        url: String
    ): Result<CustomGroupWithOrderAndList> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getCustomGroupWithOrderAndList(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId(),
                    docType = groupType.name
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun updateCustomGroupNameAndContent(
        groupType: CustomGroupType,
        docNo: Int,
        docName: String,
        list: List<String>,
        domain: String,
        url: String
    ): Result<Boolean> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateCustomList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                docType = groupType.name,
                docNo = docNo,
                docName = docName,
                list = list.toStockList()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
                .isSuccess ?: false
        }
    }

    override suspend fun addCustomGroup(
        groupType: CustomGroupType,
        docName: String,
        domain: String,
        url: String
    ): Result<NewCustomGroup> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.addCustomGroup(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    docType = groupType.name,
                    docName = docName
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun deleteCustomGroup(
        docNo: Int,
        domain: String,
        url: String
    ): Result<Boolean> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.deleteCustomGroup(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    docNo = docNo
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
                    .isSuccess ?: false
            }
        }

    override suspend fun renameCustomGroup(
        groupType: CustomGroupType,
        docNo: Int,
        newDocName: String,
        domain: String,
        url: String
    ): Result<Boolean> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateCustomGroupName(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                docType = groupType.name,
                docNo = docNo,
                docName = newDocName
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
                .isSuccess ?: false
        }
    }

    override suspend fun updateCustomGroupOrder(
        groupType: CustomGroupType,
        docNoList: List<Int>,
        domain: String,
        url: String
    ): Result<UpdateCustomGroupOrderComplete> =
        withContext(dispatcher.io()) {
            runCatching {
                val orderMap = docNoList.mapIndexed { index, docNo ->
                    docNo to index + 1
                }.toMap()
                val orderString = gson.toJson(orderMap).orEmpty()
                val response = service.updateCustomGroupOrder(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    docType = groupType.name,
                    orderMap = orderString
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun searchStocks(
        keyword: String,
        domain: String,
        url: String
    ): Result<SearchStocksResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.searchStocks(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = SearchStocksRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    keyword = keyword
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 以分號間隔
     */
    private fun List<String>.toStockList(): String {
        return this.joinToString(separator = ";")
    }
}