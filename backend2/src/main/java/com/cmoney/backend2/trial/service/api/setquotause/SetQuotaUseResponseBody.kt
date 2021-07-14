package com.cmoney.backend2.trial.service.api.setquotause

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class SetQuotaUseResponseBody(
    @SerializedName("hasTrialAuth") val hasTrialAuth: Boolean?
) : CMoneyError()