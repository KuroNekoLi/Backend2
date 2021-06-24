package com.cmoney.backend2.common.service.api.logout

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

/**
 * @param isSuccess 是否成功登出(是否成功暫停試用計時)
 */
data class LogoutComplete(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError()