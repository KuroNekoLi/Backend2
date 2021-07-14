package com.cmoney.backend2.trial.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.trial.service.api.checktrialtime.CheckTrialTimeResponseBody
import com.cmoney.backend2.trial.service.api.gettrialquota.GetTrialQuotaResponseBody
import com.cmoney.backend2.trial.service.api.setquotause.SetQuotaUseResponseBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class TrialWebImpl(
    private val gson: Gson,
    private val setting: Setting,
    private val trialService: TrialService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : TrialWeb {

    override suspend fun setQuotaUsageUse(
        memberApiParam: MemberApiParam,
        trialKey: String
    ): Result<SetQuotaUseResponseBody> = setQuotaUsageUse(trialKey)

    override suspend fun setQuotaUsageUse(trialKey: String): Result<SetQuotaUseResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.setQuotaUsageUse(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                trialKey = trialKey
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun setQuotaTimeUse(
        memberApiParam: MemberApiParam,
        trialKey: String,
        quotaUsed: Int
    ): Result<SetQuotaUseResponseBody> = setQuotaTimeUse(trialKey, quotaUsed)

    override suspend fun setQuotaTimeUse(trialKey: String, quotaUsed: Int): Result<SetQuotaUseResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.setQuotaTimeUse(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                trialKey = trialKey,
                quotaUsed = quotaUsed
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun checkTrialTime(
        memberApiParam: MemberApiParam,
        trialKey: String
    ): Result<CheckTrialTimeResponseBody> = checkTrialTime(trialKey)

    override suspend fun checkTrialTime(trialKey: String): Result<CheckTrialTimeResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.checkTrialTime(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                trialKey = trialKey
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getQuotaTime(
        memberApiParam: MemberApiParam,
        trialKey: String
    ): Result<GetTrialQuotaResponseBody> = getQuotaTime(trialKey)

    override suspend fun getQuotaTime(trialKey: String): Result<GetTrialQuotaResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = trialService.getTrialQuota(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                trialKey = trialKey
            )
            response.checkResponseBody(gson)
        }
    }
}