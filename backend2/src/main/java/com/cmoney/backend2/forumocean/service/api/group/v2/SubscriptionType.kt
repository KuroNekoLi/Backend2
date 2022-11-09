package com.cmoney.backend2.forumocean.service.api.group.v2

/**
 * 訂閱商品類型
 */
enum class SubscriptionType(val value: String) {
    /**
     * 無訂閱
     */
    NONE(""),

    /**
     * 專欄訂閱
     */
    COLUMNIST_PAID("ColumnistPaid"),

    /**
     * APP訂閱
     */
    MOBILE_PAID("MobilePaid")
}