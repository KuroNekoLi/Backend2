package com.cmoney.backend2.crm.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.crm.service.api.creatlivechat.CreateLiveChatRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class CrmWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: CrmService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : CrmWeb {

    override suspend fun createLiveChat(
        isPro: Boolean,
        domain: String,
        url: String
    ): Result<String> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = CreateLiveChatRequestBody(
                isPro = isPro
            )
            service.createLiveChat(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            ).checkResponseBody(gson)
                .liveChatUrl
                .orEmpty()
        }
    }
}