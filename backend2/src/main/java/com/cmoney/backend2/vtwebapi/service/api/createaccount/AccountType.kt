package com.cmoney.backend2.vtwebapi.service.api.createaccount

/**
 * 創建帳戶類型
 *
 * @property STOCK 股票
 * @property OPTIONS 期權
 */
enum class AccountType(val typeNum: Int) {
    STOCK(1),
    OPTIONS(2)
}