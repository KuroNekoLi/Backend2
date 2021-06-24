package com.cmoney.backend2.common.service.api.starttrialtiming

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

/**
 * @param canTrial 可否試用
 * @param trialRemainingSeconds 剩餘試用秒數
 */
data class TrialBeginStatus(
    @SerializedName("CanTrial")
    val canTrial: Boolean?,
    @SerializedName("TrialRemainingSeconds")
    val trialRemainingSeconds: Int?
): CMoneyError()