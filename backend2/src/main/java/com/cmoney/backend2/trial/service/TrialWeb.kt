package com.cmoney.backend2.trial.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.trial.service.api.checktrialtime.CheckTrialTimeResponseBody
import com.cmoney.backend2.trial.service.api.gettrialquota.GetTrialQuotaResponseBody
import com.cmoney.backend2.trial.service.api.setquotause.SetQuotaUseResponseBody

interface TrialWeb {

    val manager: GlobalBackend2Manager

    /**
     * 使用試用次數(固定扣除1)
     *
     * @param trialKey 試用金鑰
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 是否仍有試用權限
     */
    suspend fun setQuotaUsageUse(
        trialKey: String,
        domain: String = manager.getTrialSettingAdapter().getDomain(),
        url: String = "${domain}Authentication/trial-quota/usage/use"
    ): Result<SetQuotaUseResponseBody>

    /**
     * 使用試用時間(扣除[quotaUsed])
     *
     * @param trialKey 試用金鑰
     * @param quotaUsed 已使用的試用時間
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 是否仍有試用權限
     */
    suspend fun setQuotaTimeUse(
        trialKey: String,
        quotaUsed: Int,
        domain: String = manager.getTrialSettingAdapter().getDomain(),
        url: String = "${domain}Authentication/trial-quota/time/use"
    ): Result<SetQuotaUseResponseBody>

    /**
     * 確認試用時間(根據金鑰設定的機制從第一次呼叫的時候計算到期時間)
     *
     * 到期時間為 第一次呼叫時間 + 可試用時間
     *
     * @param trialKey 試用金鑰
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 剩餘試用時間
     */
    suspend fun checkTrialTime(
        trialKey: String,
        domain: String = manager.getTrialSettingAdapter().getDomain(),
        url: String = "${domain}Authentication/trial-time/check"
    ): Result<CheckTrialTimeResponseBody>

    /**
     * 取得試用金鑰剩餘的額度(時間或次數)
     *
     * @param trialKey 試用金鑰
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 試用剩餘的額度
     */
    suspend fun getQuotaTime(
        trialKey: String,
        domain: String = manager.getTrialSettingAdapter().getDomain(),
        url: String = "${domain}Authentication/trial-quota/get"
    ): Result<GetTrialQuotaResponseBody>
}