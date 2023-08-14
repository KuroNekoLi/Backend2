package com.cmoney.backend2.trial.service.api.checktrialtime

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

/**
 * 確認試用剩餘時間的回傳
 *
 * @property timeRemaining 剩餘的試用時間
 */
data class CheckTrialTimeResponseBody(
    @SerializedName("timeRemaining") val timeRemaining: Int?
): CMoneyError()