package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.commonuse.service.api.historyevent.HistoryEvents
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreference
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreferenceType
import com.cmoney.backend2.commonuse.service.api.query.QueryParam
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext

class CommonUseWebImpl(
    override val manager: GlobalBackend2Manager,
    private val commonUseService: CommonUseService,
    private val gson: Gson,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : CommonUseWeb {

    override suspend fun getRemoteConfigLabel(domain: String, url: String): Result<String> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val responseBody = commonUseService.query(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    query = QueryParam("query{member{remoteConfigLabel}}")
                )
                    .checkResponseBody(gson)

                val data = responseBody.getAsJsonObject("data")
                val member = data.getAsJsonObject("member")
                val remoteConfigLabel = member.get("remoteConfigLabel")

                if (remoteConfigLabel.isJsonNull) {
                    ""
                } else {
                    remoteConfigLabel.asString.orEmpty()
                }
            }
        }

    override suspend fun updateInvestmentPreference(
        investmentPreferenceType: InvestmentPreferenceType,
        domain: String,
        url: String
    ): Result<InvestmentPreferenceType> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val requestBody = gson.toJson(investmentPreferenceType.ids)
                val responseBody = commonUseService.query(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    query = QueryParam("mutation{updateMember{updateInvestmentRiskPreference(ids:$requestBody)}}")
                )
                    .checkResponseBody(gson)

                val data = responseBody.getAsJsonObject("data")
                val updateMember = data.getAsJsonObject("updateMember")
                val newInvestmentPreferenceIds = updateMember.get("updateInvestmentRiskPreference")

                newInvestmentPreferenceIds.let { ids ->
                    val result = gson.fromJson(ids, IntArray::class.java)
                    InvestmentPreferenceType.values().find { type ->
                        type.ids.contentEquals(result)
                    }
                } ?: InvestmentPreferenceType.None
            }
        }

    override suspend fun getInvestmentPreferences(
        domain: String,
        url: String
    ): Result<List<InvestmentPreference>> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val responseBody = commonUseService.query(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    query = QueryParam("query{member{investmentRiskPreferences{id name isChosen}}}")
                )
                    .checkResponseBody(gson)

                val data = responseBody.getAsJsonObject("data")
                val member = data.getAsJsonObject("member")
                val investmentPreference = member.get("investmentRiskPreferences")

                if (investmentPreference.isJsonNull) {
                    emptyList()
                } else {
                    gson.fromJson(
                        investmentPreference,
                        object : TypeToken<List<InvestmentPreference>>() {}.type
                    )
                }
            }
        }

    override suspend fun getCommodityHistoryEvent(
        commodityIds: List<String>,
        endCursor: String?,
        domain: String,
        url: String
    ): Result<HistoryEvents> = withContext(dispatcherProvider.io()) {
        kotlin.runCatching {
            val requestCommodityIds = gson.toJson(commodityIds)
            val requestEndCursor = gson.toJson(endCursor)
            val responseBody = commonUseService.query(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                query = QueryParam(
                    """
                        |query{
                          |notif{
                              |majorEvents(commKeys:$requestCommodityIds,after:$requestEndCursor){
                                  |edges{
                                      |node{
                                          |commKey
                                          |commName
                                          |body
                                          |link
                                          |notifyTime
                                      |}
                                  |}
                                  |pageInfo{
                                      |hasNextPage
                                      |endCursor
                                  |}
                              |}
                          |}
                        |}
                    """.trimMargin()
                )
            )
                .checkResponseBody(gson)

            val response = responseBody
                .getAsJsonObject("data")
                .getAsJsonObject("notif")
                .get("majorEvents")

            if (response.isJsonNull) {
                HistoryEvents(
                    events = null,
                    pageInfo = null
                )
            } else {
                gson.fromJson(
                    response,
                    HistoryEvents::class.java
                )
            }
        }
    }
}
