package com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns

/**
 * 使用道具類型
 *
 * @property UNUSED 未使用
 * @property IS_USING 使用中
 * @property USE_UP 使用完畢
 * @property REFUND 退款取消
 */
enum class UsageType(val typeNum: Int) {
    UNUSED(1),
    IS_USING(2),
    USE_UP(3),
    REFUND(5)
}