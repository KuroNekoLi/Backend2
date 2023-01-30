package com.cmoney.backend2.crm.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.crm.service.api.creatlivechat.CreateLiveChatRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class CrmWebImpl(
    private val setting: Setting,
    private val gson: Gson,
    private val service: CrmService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : CrmWeb {

    override suspend fun createLiveChat(isPro: Boolean): Result<String> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val requestBody = CreateLiveChatRequestBody(
                isPro = isPro
            )
            service.createLiveChat(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            ).checkResponseBody(gson)
                .liveChatUrl
                .orEmpty()
        }
    }
}