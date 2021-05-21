package com.cmoney.backend2.trial.service.api.gettrialquota

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class GetTrialQuotaResponseBody(
    @SerializedName("quota") val quota: Int?
) : CMoneyError()