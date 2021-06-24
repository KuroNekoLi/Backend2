package com.cmoney.backend2.common.service.api.loginreward

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

/**
 * @param isSuccess 是否成功給獎
 * @param cumulativeDays 已連續登入幾天
 */
data class LoginRewardComplete(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("CumulativeDays")
    val cumulativeDays: Int?
): CMoneyError()