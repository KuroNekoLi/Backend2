package com.cmoney.backend2.trial.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.trial.service.api.checktrialtime.CheckTrialTimeResponseBody
import com.cmoney.backend2.trial.service.api.gettrialquota.GetTrialQuotaResponseBody
import com.cmoney.backend2.trial.service.api.setquotause.SetQuotaUseResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class TrialWebImpl(
    override val manager: GlobalBackend2Manager,
    private val trialService: TrialService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : TrialWeb {

    override suspend fun setQuotaUsageUse(
        trialKey: String,
        domain: String,
        url: String
    ): Result<SetQuotaUseResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.setQuotaUsageUse(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                trialKey = trialKey
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun setQuotaTimeUse(
        trialKey: String,
        quotaUsed: Int,
        domain: String,
        url: String
    ): Result<SetQuotaUseResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.setQuotaTimeUse(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                trialKey = trialKey,
                quotaUsed = quotaUsed
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun checkTrialTime(
        trialKey: String,
        domain: String,
        url: String
    ): Result<CheckTrialTimeResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.checkTrialTime(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                trialKey = trialKey
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getQuotaTime(
        trialKey: String,
        domain: String,
        url: String
    ): Result<GetTrialQuotaResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.getTrialQuota(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                trialKey = trialKey
            )
            response.checkResponseBody(gson)
        }
    }
}