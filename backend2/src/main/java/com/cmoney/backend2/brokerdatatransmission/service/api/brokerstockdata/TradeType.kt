package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata

/**
 * 股票類型
 */
enum class TradeType(val value: Byte) {

    /**
     * 現股
     */
    Spot(0),

    /**
     * 融資
     */
    MarginTrading(1),

    /**
     * 融券
     */
    ShortSelling(2),

    /**
     * 興櫃
     */
    Emerging(3);

    companion object {
        internal fun fromValue(value: Byte): TradeType? {
            return values().find { tradeType ->
                tradeType.value == value
            }
        }
    }

}
