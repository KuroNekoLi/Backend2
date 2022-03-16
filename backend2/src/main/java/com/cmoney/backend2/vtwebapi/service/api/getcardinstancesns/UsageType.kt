package com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns

/**
 * 使用道具類型
 */
enum class UsageType(val typeNum: Int) {
    /**
     * 未使用
     */
    UNUSED(1),

    /**
     * 使用中
     */
    IS_USING(2),

    /**
     * 使用完畢
     */
    USE_UP(3),

    /**
     * 退款取消
     */
    REFUND(5)
}