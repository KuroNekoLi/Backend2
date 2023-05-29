package com.cmoney.backend2.trial.service.api.gettrialquota

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

/**
 * 取得試用剩餘額度回傳
 *
 * @property quota 試用剩餘額度(次數或時間)
 */
data class GetTrialQuotaResponseBody(
    @SerializedName("quota") val quota: Int?
) : CMoneyError()