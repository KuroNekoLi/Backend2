package com.cmoney.backend2.virtualtrading2.service.api.createaccount

import com.google.gson.annotations.SerializedName

/**
 * 建立帳號請求內容
 *
 * @property accountInvestType 投資帳戶類型 (現股 : 1 / 期權 : 2)
 * @property cardSn 道具卡序號，沒有道具卡填0(免費創建)
 *
 */
data class CreateAccountRequestBody(
    @SerializedName("AccountInvestType")
    val accountInvestType: Int,
    @SerializedName("CardSn")
    val cardSn: Long
)
