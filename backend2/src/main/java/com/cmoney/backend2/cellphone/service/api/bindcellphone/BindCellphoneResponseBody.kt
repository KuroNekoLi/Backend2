package com.cmoney.backend2.cellphone.service.api.bindcellphone

import com.google.gson.annotations.SerializedName

data class BindCellphoneResponseBody(
    /**
     * 驗證碼有效時間(秒)
     */
    @SerializedName("VerifyCodeDuration")
    val verifyCodeDuration: Int?,

    /**
     * 重送驗證碼間隔時間(秒)
     */
    @SerializedName("VerifyCodeResendInterval")
    val verifyCodeResendInterval: Int?
)