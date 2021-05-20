package com.cmoney.backend2.cellphone.service.api.checkverifycode

import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數錯誤  -1-服務尚未初始化 2-驗證碼有誤
 */
data class CellphoneCheckVerifyCode(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)