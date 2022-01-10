package com.cmoney.backend2.customgroup2.service.api.data

/**
 * 市場類型
 *
 */
sealed class MarketTypeV2(
    open val type: Int,
    open val subType: Int
) {

    /**
     * 上市
     */
    sealed class Tse(
        override val type: Int = 2,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 上市子類型
         */
        /**
         * 國內/外指數股票型ETF
         */
        object DomesticAndForeignIndexStockEtf : Tse(subType = 160)

        /**
         * 加掛式指數股票型ETF
         */
        object AddOnIndexStockEtf : Tse(subType = 195)

        /**
         * 槓桿指數股票型ETF
         */
        object LeveragedIndexStockEtf : Tse(subType = 196)

        /**
         * 反向指數股票型ETF
         */
        object InverseIndexStockEtf : Tse(subType = 198)

        /**
         * 期信指數股票型ETF
         */
        object FuturesIndexStockEtf : Tse(subType = 200)

        /**
         * 債券指數股票型ETF
         */
        object BondIndexStockEtf : Tse(subType = 206)

        /**
         * 加掛式債券指數型ETF
         */
        object AddOnBondIndexEtf : Tse(subType = 207)

        /**
         * ETN
         */
        object Etn : Tse(subType = 211)

        /**
         * 股票
         */
        object Stock : Tse(subType = 90)

        /**
         * 存託憑證
         */
        object DepositoryReceipt : Tse(subType = 158)

        companion object {

            fun getAll(): List<Tse> {
                return listOf(
                    DomesticAndForeignIndexStockEtf,
                    AddOnIndexStockEtf,
                    LeveragedIndexStockEtf,
                    InverseIndexStockEtf,
                    FuturesIndexStockEtf,
                    BondIndexStockEtf,
                    AddOnBondIndexEtf,
                    Etn,
                    Stock,
                    DepositoryReceipt
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 上櫃
     */
    sealed class Otc(
        override val type: Int = 3,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 國內/外指數股票型ETF
         */
        object DomesticAndForeignIndexStockEtf : Otc(subType = 160)

        /**
         * 債券指數股票型ETF
         */
        object BondIndexStockEtf : Otc(subType = 206)

        /**
         * ETN
         */
        object Etn : Otc(subType = 211)

        /**
         * 股票
         */
        object Stock : Otc(subType = 90)

        companion object {
            fun getAll(): List<Otc> {
                return listOf(
                    DomesticAndForeignIndexStockEtf,
                    BondIndexStockEtf,
                    Etn,
                    Stock
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 期貨
     */
    sealed class Future(
        override val type: Int = 4,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 商品期貨
         */
        object Commodity : Future(subType = 67)

        /**
         * 股價指數期貨
         */
        object StockPriceIndex : Future(subType = 73)

        /**
         * 股票期貨
         */
        object Stock : Future(subType = 83)

        companion object {
            fun getAll(): List<Future> {
                return listOf(
                    Commodity,
                    StockPriceIndex,
                    Stock
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 選擇權
     */
    sealed class Option(
        override val type: Int = 5,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 股票選擇權
         */
        object Stock : Option(subType = 83)

        /**
         * 匯率選擇權
         */
        object ExchangeRate : Option(subType = 69)

        /**
         * 商品選擇權
         */
        object Commodity : Option(subType = 67)

        /**
         * 股價指數選擇權
         */
        object StockPriceIndex : Option(subType = 73)

        companion object {
            fun getAll(): List<Option> {
                return listOf(
                    Stock,
                    ExchangeRate,
                    Commodity,
                    StockPriceIndex
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 興櫃
     */
    sealed class Emerging(
        override val type: Int = 6,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 股票
         */
        object Stock : Emerging(subType = 90)

        companion object {
            fun getAll(): List<Emerging> {
                return listOf(
                    Stock
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 期貨盤後
     */
    sealed class FutureAfterMarket(
        override val type: Int = 7,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 商品期貨
         */
        object Commodity : FutureAfterMarket(subType = 67)

        /**
         * 匯率期貨
         */
        object ExchangeRate : FutureAfterMarket(subType = 69)

        /**
         * 股價指數期貨
         */
        object StockPriceIndex : FutureAfterMarket(subType = 73)

        companion object {
            fun getAll(): List<FutureAfterMarket> {
                return listOf(
                    ExchangeRate,
                    Commodity,
                    StockPriceIndex
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 選擇權盤後
     */
    sealed class OptionAfterMarket(
        override val type: Int = 8,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 商品選擇權
         */
        object Commodity : OptionAfterMarket(subType = 67)

        /**
         * 匯率選擇權
         */
        object ExchangeRate : OptionAfterMarket(subType = 69)

        /**
         * 股價指數選擇權
         */
        object StockPriceIndex : OptionAfterMarket(subType = 73)

        companion object {
            fun getAll(): List<OptionAfterMarket> {
                return listOf(
                    ExchangeRate,
                    Commodity,
                    StockPriceIndex
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 上市權證
     */
    sealed class TseWarrant(
        override val type: Int = 21,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 認購
         */
        object Call : TseWarrant(subType = 161)

        /**
         * 認售
         */
        object Put : TseWarrant(subType = 162)

        companion object {
            fun getAll(): List<TseWarrant> {
                return listOf(
                    Call,
                    Put
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 上櫃權證
     */
    sealed class OtcWarrant(
        override val type: Int = 31,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 認購
         */
        object Call : OtcWarrant(subType = 163)

        /**
         * 認售
         */
        object Put : OtcWarrant(subType = 164)

        companion object {
            fun getAll(): List<OtcWarrant> {
                return listOf(
                    Call,
                    Put
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 美股
     */
    sealed class UsaStock(
        override val type: Int = 40,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 普通股
         */
        object CommonStock : UsaStock(subType = 1)

        /**
         * 美國存託憑證
         */
        object AmericanDepositaryReceipt : UsaStock(subType = 2)

        /**
         * 附認股權證普通股
         */
        object OrdinarySharesWithWarrants : UsaStock(subType = 7)

        /**
         * 股本權證
         */
        object EquityWarrants : UsaStock(subType = 3)

        /**
         * 特別股
         */
        object SpecialShares : UsaStock(subType = 4)

        /**
         * 不動產投資信託
         */
        object RealEstateInvestmentTrust : UsaStock(subType = 5)

        /**
         * 業主有限合夥股
         */
        object OwnersLimitedPartnershipShares : UsaStock(subType = 6)

        /**
         * 封閉型基金
         */
        object ClosedEndFund : UsaStock(subType = 8)

        /**
         * 認股權
         */
        object StockOption : UsaStock(subType = 9)

        /**
         * 紐約註冊股份
         */
        object NewYorkRegisteredShares : UsaStock(subType = 10)

        /**
         * 追蹤股
         */
        object TrackingStock : UsaStock(subType = 11)

        /**
         * 有限合夥股
         */
        object LimitedPartnershipShares : UsaStock(subType = 12)

        /**
         * 權利金信託
         */
        object RoyaltyTrust : UsaStock(subType = 13)

        /**
         * Etf
         */
        object Etf : UsaStock(subType = 14)

        companion object {
            fun getAll(): List<UsaStock> {
                return listOf(
                    CommonStock,
                    AmericanDepositaryReceipt,
                    OrdinarySharesWithWarrants,
                    EquityWarrants,
                    SpecialShares,
                    RealEstateInvestmentTrust,
                    OwnersLimitedPartnershipShares,
                    ClosedEndFund,
                    StockOption,
                    NewYorkRegisteredShares,
                    TrackingStock,
                    LimitedPartnershipShares,
                    RoyaltyTrust,
                    Etf
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 國際指數
     */
    sealed class InternationalIndex(
        override val type: Int = 70,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 美國
         */
        object UnitedState : InternationalIndex(subType = 840)

        /**
         * 法國
         */
        object France : InternationalIndex(subType = 250)

        /**
         * 英國
         */
        object UnitedKingdom : InternationalIndex(subType = 826)

        /**
         * 德國
         */
        object Germany : InternationalIndex(subType = 276)

        /**
         * 香港
         */
        object HongKong : InternationalIndex(subType = 344)

        /**
         * 韓國
         */
        object SouthKorea : InternationalIndex(subType = 410)

        /**
         * 日本
         */
        object Japan : InternationalIndex(subType = 392)

        /**
         * 俄羅斯
         */
        object Russia : InternationalIndex(subType = 643)

        /**
         * 中國
         */
        object China : InternationalIndex(subType = 156)

        /**
         * 臺灣
         */
        object Taiwan : InternationalIndex(subType = 158)

        companion object {
            fun getAll(): List<InternationalIndex> {
                return listOf(
                    UnitedState,
                    France,
                    UnitedKingdom,
                    Germany,
                    HongKong,
                    SouthKorea,
                    Japan,
                    Russia,
                    China,
                    Taiwan
                )
            }

            fun valueOf(subType: Int): MarketTypeV2? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    companion object {

        /**
         * 取得所有的市場類型
         */
        fun getAll(): Map<Int, List<MarketTypeV2>> {
            val marketTypes = mutableListOf<MarketTypeV2>()
                .apply {
                    addAll(Tse.getAll())
                    addAll(Otc.getAll())
                    addAll(Future.getAll())
                    addAll(Option.getAll())
                    addAll(FutureAfterMarket.getAll())
                    addAll(OptionAfterMarket.getAll())
                    addAll(TseWarrant.getAll())
                    addAll(OtcWarrant.getAll())
                    addAll(Emerging.getAll())
                    addAll(UsaStock.getAll())
                    addAll(InternationalIndex.getAll())
                }
            val marketTypeMap = marketTypes.groupBy { marketType ->
                marketType.type
            }
            return marketTypeMap
        }

        fun valueOf(type: Int, subType: Int): MarketTypeV2? {
            val marketTypeMap = getAll()
            val marketType = marketTypeMap[type]?.find {
                it.subType == subType
            }
            return marketType
        }
    }
}