package com.cmoney.backend2.data.service

import android.net.Uri
import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.data.extension.checkApiError
import com.cmoney.backend2.data.service.api.FundIdData
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import kotlinx.coroutines.withContext

class DataWebImpl(
    private val service: DataService,
    private val setting: Setting,
    override val baseHost: String = DEFAULT_HOST,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : DataWeb {

    override suspend fun getFundIdData(
        host: String,
        fundId: Int,
        params: String
    ): Result<FundIdData> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val requestUrl = Uri.parse("${baseHost}api/ChipK")
                    .buildUpon()
                    .appendQueryParameter("fundId", fundId.toString())
                    .appendQueryParameter("params", params)
                    .appendQueryParameter("guid", setting.identityToken.getMemberGuid())
                    .appendQueryParameter("appId", setting.appId.toString())
                    .build()
                service.getFundIdData(
                    authToken = setting.accessToken.createAuthorizationBearer(),
                    url = requestUrl.toString()
                )
                    .checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .checkApiError()
                    .toRealResponse()
            }
        }

    companion object {
        private const val DEFAULT_HOST = "https://datasv.cmoney.tw:5001/"
    }
}
