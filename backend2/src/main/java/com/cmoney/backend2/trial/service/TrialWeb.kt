package com.cmoney.backend2.trial.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.trial.service.api.checktrialtime.CheckTrialTimeResponseBody
import com.cmoney.backend2.trial.service.api.gettrialquota.GetTrialQuotaResponseBody
import com.cmoney.backend2.trial.service.api.setquotause.SetQuotaUseResponseBody

interface TrialWeb {

    @Deprecated("ApiParam no longer required.")
    suspend fun setQuotaUsageUse(
            memberApiParam: MemberApiParam,
            trialKey: String
    ): Result<SetQuotaUseResponseBody>

    suspend fun setQuotaUsageUse(trialKey: String): Result<SetQuotaUseResponseBody>

    @Deprecated("ApiParam no longer required.")
    suspend fun setQuotaTimeUse(
            memberApiParam: MemberApiParam,
            trialKey: String,
            quotaUsed: Int
    ): Result<SetQuotaUseResponseBody>

    suspend fun setQuotaTimeUse(trialKey: String, quotaUsed: Int): Result<SetQuotaUseResponseBody>

    @Deprecated("ApiParam no longer required.")
    suspend fun checkTrialTime(
            memberApiParam: MemberApiParam,
            trialKey: String
    ): Result<CheckTrialTimeResponseBody>

    suspend fun checkTrialTime(trialKey: String): Result<CheckTrialTimeResponseBody>

    @Deprecated("ApiParam no longer required.")
    suspend fun getQuotaTime(
            memberApiParam: MemberApiParam,
            trialKey: String
    ): Result<GetTrialQuotaResponseBody>

    suspend fun getQuotaTime(trialKey: String): Result<GetTrialQuotaResponseBody>

}