package com.cmoney.backend2.vtwebapi.service.api.createaccount

/**
 * 創建帳戶類型
 */
enum class AccountType(val typeNum: Int) {
    /**
     * 股票
     */
    STOCK(1),

    /**
     * 期權
     */
    OPTIONS(2)
}