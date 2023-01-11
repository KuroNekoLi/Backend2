package com.cmoney.backend2.virtualtrading2.web.createaccount

/**
 *
 * 建立帳號要求內容
 *
 * @property accountInvestType 投資帳戶類型
 * @property cardSn 道具卡序號，沒有道具卡填0(免費創建)
 *
 */
data class CreateAccountRequest(
    val accountInvestType: AccountInvestType,
    val cardSn: Long = 0
) {
    /**
     * 投資帳戶類型
     */
    sealed class AccountInvestType(
        val value: Int
    ) {
        /**
         * 現股
         */
        object Stock : AccountInvestType(value = 1)

        /**
         * 期權
         */
        object Options : AccountInvestType(value = 2)
    }
}
