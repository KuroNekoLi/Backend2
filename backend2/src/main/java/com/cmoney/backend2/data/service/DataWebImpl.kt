package com.cmoney.backend2.data.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.data.service.api.FundIdData
import com.cmoney.backend2.data.service.api.checkApiError
import kotlinx.coroutines.withContext

class DataWebImpl(
    private val service: DataService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : DataWeb {

    override suspend fun getFundIdData(fundId: Int, params: String): Result<FundIdData> =
        withContext(dispatcher.io()) {
            runCatching {
                service.getFundIdData(
                    authToken = setting.accessToken.createAuthorizationBearer(),
                    fundId = fundId,
                    params = params,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
                    .checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .checkApiError()
                    .toRealResponse()
            }
        }

}
