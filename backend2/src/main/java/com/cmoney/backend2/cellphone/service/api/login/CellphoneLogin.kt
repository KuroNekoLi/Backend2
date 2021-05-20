package com.cmoney.backend2.cellphone.service.api.login

import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數錯誤 -1-服務尚未初始化 2-密碼錯誤 3-此手機已註冊但尚未驗證 4-查無此會員存在 5-無法取得會員資料 6-AppId不存在 7-取得追訊頻道發生錯誤
 */
data class CellphoneLogin(

    @SerializedName("Guid")
    val guid: String?,

    @SerializedName("AuthToken")
    val authToken: String?,

    @SerializedName("MemberPk")
    val memberPk: Int?,

    @SerializedName("IsFirstLogin")
    val isFirstLogin: Boolean?,

    @SerializedName("TrialRemainingSeconds")
    val trialRemainingSeconds: Int?
)