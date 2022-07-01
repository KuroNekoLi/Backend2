package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.parseServerException
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.commonuse.service.api.query.QueryParam
import com.google.gson.Gson
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
}
