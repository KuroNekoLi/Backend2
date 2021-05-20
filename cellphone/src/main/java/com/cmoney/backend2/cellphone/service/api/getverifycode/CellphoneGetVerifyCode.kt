package com.cmoney.backend2.cellphone.service.api.getverifycode

import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數錯誤 -1-服務尚未初始化 2-重複註冊檢查發生錯誤 3-手機已被註冊 4-取得驗證碼失敗 5-發送驗碼碼簡訊失敗
 */
data class CellphoneGetVerifyCode(
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