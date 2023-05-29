package com.cmoney.backend2.trial.service.api.setquotause

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

/**
 * 試用額度使用回傳
 *
 * @property hasTrialAuth true 有試用權限，false 無試用權限
 */
data class SetQuotaUseResponseBody(
    @SerializedName("hasTrialAuth") val hasTrialAuth: Boolean?
) : CMoneyError()