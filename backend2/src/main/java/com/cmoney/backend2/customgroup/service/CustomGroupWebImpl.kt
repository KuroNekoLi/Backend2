package com.cmoney.backend2.customgroup.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
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
    private val gson: Gson,
    private val service: CustomGroupService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : CustomGroupWeb {

    override suspend fun getCustomGroupListIncludeOrder(groupType: CustomGroupType): Result<List<SingleGroupWithOrder>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getCustomGroupWithOrder(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId,
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
        docNo: Int
    ): Result<List<String>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getCustomGroupContent(
                authorization = setting.accessToken.createAuthorizationBearer(),
                guid = setting.identityToken.getMemberGuid(),
                appId = setting.appId,
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

    override suspend fun getCustomGroupWithOrderAndList(
        apiParam: MemberApiParam,
        groupType: CustomGroupType
    ): Result<CustomGroupWithOrderAndList> = getCustomGroupWithOrderAndList(groupType)

    override suspend fun getCustomGroupWithOrderAndList(groupType: CustomGroupType): Result<CustomGroupWithOrderAndList> =
        getCustomGroupListIncludeOrderAndContent(groupType)

    override suspend fun getCustomGroupListIncludeOrderAndContent(groupType: CustomGroupType): Result<CustomGroupWithOrderAndList> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getCustomGroupWithOrderAndList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId,
                    docType = groupType.name
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun updateCustomList(
        apiParam: MemberApiParam,
        groupType: CustomGroupType,
        docNo: Int,
        docName: String,
        list: List<String>
    ): Result<Boolean> = updateCustomList(groupType, docNo, docName, list)

    override suspend fun updateCustomList(
        groupType: CustomGroupType,
        docNo: Int,
        docName: String,
        list: List<String>
    ): Result<Boolean> = updateCustomGroupNameAndContent(groupType, docNo, docName, list)

    override suspend fun updateCustomGroupNameAndContent(
        groupType: CustomGroupType,
        docNo: Int,
        docName: String,
        list: List<String>
    ): Result<Boolean> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateCustomList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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
        apiParam: MemberApiParam,
        groupType: CustomGroupType,
        docName: String
    ): Result<NewCustomGroup> = addCustomGroup(groupType, docName)

    override suspend fun addCustomGroup(
        groupType: CustomGroupType,
        docName: String
    ): Result<NewCustomGroup> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.addCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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
        apiParam: MemberApiParam,
        docNo: Int
    ): Result<Boolean> = deleteCustomGroup(docNo)

    override suspend fun deleteCustomGroup(docNo: Int): Result<Boolean> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.deleteCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    docNo = docNo
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
                    .isSuccess ?: false
            }
        }

    /**
     * 更名自選股清單
     */
    override suspend fun renameCustomGroup(
        apiParam: MemberApiParam,
        groupType: CustomGroupType,
        docNo: Int,
        newDocName: String
    ): Result<Boolean> = renameCustomGroup(groupType, docNo, newDocName)

    override suspend fun renameCustomGroup(
        groupType: CustomGroupType,
        docNo: Int,
        newDocName: String
    ): Result<Boolean> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateCustomGroupName(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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

    /**
     * 排序自選股清單
     */
    override suspend fun updateCustomGroupOrder(
        apiParam: MemberApiParam,
        groupType: CustomGroupType,
        docNoList: List<Int>
    ): Result<UpdateCustomGroupOrderComplete> = updateCustomGroupOrder(groupType, docNoList)

    override suspend fun updateCustomGroupOrder(
        groupType: CustomGroupType,
        docNoList: List<Int>
    ): Result<UpdateCustomGroupOrderComplete> =
        withContext(dispatcher.io()) {
            runCatching {
                val orderMap = docNoList.mapIndexed { index, docNo ->
                    docNo to index + 1
                }.toMap()
                val orderString = gson.toJson(orderMap).orEmpty()
                val response = service.updateCustomGroupOrder(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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
        keyword: String
    ): Result<SearchStocksResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.searchStocks(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = SearchStocksRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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