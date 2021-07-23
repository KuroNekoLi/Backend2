package com.cmoney.backend2.customgroup2.service.api.data

/**
 * 市場類型
 */
enum class MarketType(val value: String) {
    /**
     * 上櫃
     */
    OTC("上櫃"),

    /**
     * 興櫃
     */
    EMERGING("興櫃"),

    /**
     * 上市
     */
    TSE("上市"),

    /**
     * 美股ETF
     */
    USA_ETF("美股ETF"),

    /**
     * 美股
     */
    USA("美股");

    companion object {

        /**
         * 根據字串回傳市場類型
         *
         * @param value 字串
         * @return
         */
        fun fromValue(value: String?): MarketType? {
            value ?: return null
            for (type in values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }
    }
}