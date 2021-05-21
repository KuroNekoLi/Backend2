package com.cmoney.backend2.cellphone.service.api.register

import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數錯誤 -1-服務尚未初始化 2-重複註冊檢查發生錯誤 3-手機已被註冊 4-寫入註冊表失敗 5-建立會員失敗 6-更新註冊狀態失敗 7-短時間重複呼叫
 */
data class CellphoneRegister(

    /**
     * 註冊是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)