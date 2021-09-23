package com.cmoney.backend2.customgroup2.service.api.data

/**
 * 文件市場類別
 *
 * @property value 數值
 */
sealed class DocMarketType(val value: String) {
    object All: DocMarketType("All")
    /**
     * 台股
     */
    object Stock: DocMarketType("Stock")

    /**
     * 美股
     */
    object UsaStock: DocMarketType("Ustock")
    object Broker: DocMarketType("Broker")
    object Warrant: DocMarketType("Warrant")
    object Bond: DocMarketType("Bond")
    object BrokerBranch: DocMarketType("BrokerBranch")

    companion object {
        const val KEY = "marketType"

        fun fromValue(value: String): DocMarketType? {
            for (type in values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }

        fun values(): List<DocMarketType> {
            return listOf(
                All,
                Stock,
                UsaStock,
                Broker,
                Warrant,
                Bond,
                BrokerBranch
            )
        }
    }
}
