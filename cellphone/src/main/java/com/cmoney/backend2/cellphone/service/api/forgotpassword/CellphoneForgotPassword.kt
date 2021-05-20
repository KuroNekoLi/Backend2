package com.cmoney.backend2.cellphone.service.api.forgotpassword

import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數錯誤 -1-服務尚未初始化 2-會員不存在 3-取得驗證碼失敗 4-發送忘記密碼簡訊失敗 5-已申請,請等一分鐘
 */
data class CellphoneForgotPassword(

    /**
     * 是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)