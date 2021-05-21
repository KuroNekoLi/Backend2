package com.cmoney.backend2.trial.service.api.checktrialtime

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class CheckTrialTimeResponseBody(
    @SerializedName("timeRemaining") val timeRemaining: Int?
): CMoneyError()