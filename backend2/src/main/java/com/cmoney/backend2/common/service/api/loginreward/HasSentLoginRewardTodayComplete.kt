package com.cmoney.backend2.common.service.api.loginreward

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

/**
 * @param hasSent 是否已發送獎勵
 * @param cumulativeDays 已連續登入幾天
 */
data class HasSentLoginRewardTodayComplete (
    @SerializedName("HasSent")
    val hasSent: Boolean?,
    @SerializedName("CumulativeDays")
    val cumulativeDays: Int?
): CMoneyError()