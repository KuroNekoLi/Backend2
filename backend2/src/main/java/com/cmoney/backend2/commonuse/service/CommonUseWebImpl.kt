package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.parseServerException
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreference
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreferenceType
import com.cmoney.backend2.commonuse.service.api.query.QueryParam
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext

class CommonUseWebImpl(
    override val baseHost: String,
    private val commonUseService: CommonUseService,
    private val setting: Setting,
    private val gson: Gson,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : CommonUseWeb {

    companion object {
        private const val servicePath = "commonuse"
    }

    override suspend fun getRemoteConfigLabel(host: String): Result<String> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val response = commonUseService.query(
                    url = "$host$servicePath/graphql",
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    query = QueryParam("query{member{remoteConfigLabel}}")
                )
                if (response.code() >= 400) {
                    throw response.parseServerException(gson)
                }

                val data = response.body()?.getAsJsonObject("data")
                val member = data?.getAsJsonObject("member")
                val remoteConfigLabel = member?.get("remoteConfigLabel")

                if (remoteConfigLabel?.isJsonNull == true) {
                    ""
                } else {
                    remoteConfigLabel?.asString.orEmpty()
                }
            }
        }

    override suspend fun updateInvestmentPreference(
        host: String,
        investmentPreferenceType: InvestmentPreferenceType
    ): Result<InvestmentPreferenceType> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val requestBody = gson.toJson(investmentPreferenceType.ids)
                val response = commonUseService.query(
                    url = "$host$servicePath/graphql",
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    query = QueryParam("mutation{updateMember{updateInvestmentRiskPreference(ids:$requestBody)}}")
                )
                if (response.code() >= 400) {
                    throw  response.parseServerException(gson)
                }

                val data = response.body()?.getAsJsonObject("data")
                val updateMember = data?.getAsJsonObject("updateMember")
                val newInvestmentPreferenceIds = updateMember?.get("updateInvestmentRiskPreference")

                newInvestmentPreferenceIds?.let { ids ->
                    val result = gson.fromJson(ids, IntArray::class.java)
                    InvestmentPreferenceType.values().find { type ->
                        type.ids.contentEquals(result)
                    }
                } ?: InvestmentPreferenceType.None
            }
        }

    override suspend fun getInvestmentPreferences(host: String): Result<List<InvestmentPreference>> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val response = commonUseService.query(
                    url = "$host$servicePath/graphql",
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    query = QueryParam("query{member{investmentRiskPreferences{id name isChosen}}}")
                )
                if (response.code() >= 400) {
                    throw response.parseServerException(gson)
                }

                val data = response.body()?.getAsJsonObject("data")
                val member = data?.getAsJsonObject("member")
                val investmentPreference = member?.get("investmentRiskPreferences")
                if (investmentPreference?.isJsonNull == true) {
                    emptyList()
                } else {
                    gson.fromJson(
                        investmentPreference,
                        object : TypeToken<List<InvestmentPreference>>() {}.type
                    )
                }
            }
        }
}
