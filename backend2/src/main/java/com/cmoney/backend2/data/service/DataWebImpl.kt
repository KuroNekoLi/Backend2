package com.cmoney.backend2.data.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.data.extension.checkApiError
import com.cmoney.backend2.data.service.api.FundIdData
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import kotlinx.coroutines.withContext

class DataWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: DataService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : DataWeb {

    override suspend fun getFundIdData(
        fundId: Int,
        params: String,
        domain: String,
        url: String
    ): Result<FundIdData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getFundIdData(
                    url = url,
                    authToken = manager.getAccessToken().createAuthorizationBearer(),
                    fundId = fundId,
                    params = params
                )
                    .checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .checkApiError()
                    .toRealResponse()
            }
        }
}
