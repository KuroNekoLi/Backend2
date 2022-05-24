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
         * 股票
         */
        object Stock : Tse(subType = 90)

        /**
         * 指數
         */
        object Index : Tse(subType = 150)

        /**
         * 特別股
         */
        object Special : Tse(subType = 151)

        /**
         * 存託憑證
         */
        object DepositoryReceipt : Tse(subType = 158)

        /**
         * 國內/外指數股票型ETF
         */
        object DomesticAndForeignIndexStockEtf : Tse(subType = 160)

        /**
         * 政府公債
         */
        object GovernmentBonds : Tse(subType = 168)

        /**
         * REITs
         */
        object REITs : Tse(subType = 172)

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
         * 國內成分ETN
         */
        object DomesticEtn : Tse(subType = 300)

        /**
         * 國外成分ETN
         */
        object ForeignEtn : Tse(subType = 301)

        /**
         * 槓桿型及反向型成分ETN
         */
        object LeveragedAndInverseComponentsEtn : Tse(subType = 302)

        companion object {

            fun getAll(): List<Tse> {
                return listOf(
                    Stock,
                    Index,
                    Special,
                    DepositoryReceipt,
                    DomesticAndForeignIndexStockEtf,
                    GovernmentBonds,
                    REITs,
                    AddOnIndexStockEtf,
                    LeveragedIndexStockEtf,
                    InverseIndexStockEtf,
                    FuturesIndexStockEtf,
                    BondIndexStockEtf,
                    AddOnBondIndexEtf,
                    DomesticEtn,
                    ForeignEtn,
                    LeveragedAndInverseComponentsEtn
                )
            }

            fun valueOf(subType: Int): Tse? {
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
         * 股票
         */
        object Stock : Otc(subType = 90)

        /**
         * 指數
         */
        object Index : Otc(subType = 150)

        /**
         * 特別股
         */
        object Special : Otc(subType = 151)

        /**
         * 可轉政府公債
         */
        object ConvertibleGovernmentBonds : Otc(subType = 152)

        /**
         * 國內/外指數股票型ETF
         */
        object DomesticAndForeignIndexStockEtf : Otc(subType = 160)

        /**
         * 債券指數股票型ETF
         */
        object BondIndexStockEtf : Otc(subType = 206)

        /**
         * 國內成分ETN
         */
        object DomesticEtn : Otc(subType = 300)

        /**
         * 國外成分ETN
         */
        object ForeignEtn : Otc(subType = 301)

        /**
         * 槓桿型及反向型成分ETN
         */
        object LeveragedAndInverseComponentsEtn : Otc(subType = 302)

        companion object {
            fun getAll(): List<Otc> {
                return listOf(
                    Stock,
                    Index,
                    Special,
                    ConvertibleGovernmentBonds,
                    DomesticAndForeignIndexStockEtf,
                    BondIndexStockEtf,
                    DomesticEtn,
                    ForeignEtn,
                    LeveragedAndInverseComponentsEtn
                )
            }

            fun valueOf(subType: Int): Otc? {
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
         * 匯率期貨
         */
        object ExchangeRate : Future(subType = 69)

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
                    ExchangeRate,
                    StockPriceIndex,
                    Stock
                )
            }

            fun valueOf(subType: Int): Future? {
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
         * 商品選擇權
         */
        object Commodity : Option(subType = 67)


        /**
         * 匯率選擇權
         */
        object ExchangeRate : Option(subType = 69)

        /**
         * 股價指數選擇權
         */
        object StockPriceIndex : Option(subType = 73)

        /**
         * 股票選擇權
         */
        object Stock : Option(subType = 83)

        companion object {
            fun getAll(): List<Option> {
                return listOf(
                    Stock,
                    ExchangeRate,
                    Commodity,
                    StockPriceIndex
                )
            }

            fun valueOf(subType: Int): Option? {
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

        /**
         * 指數
         */
        object Index : Emerging(subType = 150)

        companion object {
            fun getAll(): List<Emerging> {
                return listOf(
                    Stock,
                    Index
                )
            }

            fun valueOf(subType: Int): Emerging? {
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

            fun valueOf(subType: Int): FutureAfterMarket? {
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

            fun valueOf(subType: Int): OptionAfterMarket? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 指數彙編
     */
    sealed class IndexCompilation(
        override val type: Int = 10,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * CM產業
         */
        object CmIndustry : IndexCompilation(subType = 1)

        /**
         * 概念股
         */
        object Concept : IndexCompilation(subType = 2)

        /**
         * 集團股
         */
        object EnterpriseGroup: IndexCompilation(subType = 3)

        companion object {
            fun getAll(): List<IndexCompilation> {
                return listOf(
                    CmIndustry,
                    Concept,
                    EnterpriseGroup
                )
            }

            fun valueOf(subType: Int): IndexCompilation? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 上市創新版
     */
    sealed class TseInnovation(
        override val type: Int = 11,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 創新版
         */
        object Innovative : TseInnovation(subType = 91)


        companion object {
            fun getAll(): List<TseInnovation> {
                return listOf(
                    Innovative
                )
            }

            fun valueOf(subType: Int): TseInnovation? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 興櫃戰略新版
     */
    sealed class OtcStrategy(
        override val type: Int = 12,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 戰略版
         */
        object Strategy : OtcStrategy(subType = 92)


        companion object {
            fun getAll(): List<OtcStrategy> {
                return listOf(
                    Strategy
                )
            }

            fun valueOf(subType: Int): OtcStrategy? {
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

            fun valueOf(subType: Int): TseWarrant? {
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

            fun valueOf(subType: Int): OtcWarrant? {
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
         * 附認股權證普通股
         */
        object OrdinarySharesWithWarrants : UsaStock(subType = 7)

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

            fun valueOf(subType: Int): UsaStock? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 口袋場外
     */
    sealed class PocketOverTheCounter(
        override val type: Int = 41,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * ETN
         */
        object Etn : PocketOverTheCounter(subType = 1)

        /**
         * REITs
         */
        object REITs : PocketOverTheCounter(subType = 2)

        /**
         * 存託憑證
         */
        object DepositoryReceipt : PocketOverTheCounter(subType = 3)

        /**
         * 有限合夥
         */
        object LimitedPartnership : PocketOverTheCounter(subType = 4)

        /**
         * 做多2倍ETN
         */
        object Long2ETN : PocketOverTheCounter(subType = 5)

        /**
         * 做多3倍ETN
         */
        object Long3ETN : PocketOverTheCounter(subType = 6)

        /**
         * 附認股權股票
         */
        object StockOption : PocketOverTheCounter(subType = 7)

        /**
         * 封閉型基金
         */
        object ClosedEndFund : PocketOverTheCounter(subType = 8)

        /**
         * 特別股
         */
        object SpecialShares : PocketOverTheCounter(subType = 9)

        /**
         * 特別股(ETD)
         */
        object SpecialSharesETD : PocketOverTheCounter(subType = 10)

        /**
         * 單位信託
         */
        object UnitTrust : PocketOverTheCounter(subType = 11)

        /**
         * 普通股
         */
        object CommonStock : PocketOverTheCounter(subType = 12)

        /**
         * 普開放型基金通股
         */
        object OpenEndFund : PocketOverTheCounter(subType = 13)

        /**
         * 開採權信託
         */
        object MiningRightsTrust : PocketOverTheCounter(subType = 14)

        /**
         * 權證
         */
        object Warrant : PocketOverTheCounter(subType = 15)

        /**
         * 作空1倍ETN
         */
        object Short1ETN : PocketOverTheCounter(subType = 16)

        /**
         * 作空3倍ETN
         */
        object Short3ETN : PocketOverTheCounter(subType = 17)

        /**
         * 紐約註冊股份
         */
        object NewYorkRegisteredShares : PocketOverTheCounter(subType = 18)

        companion object {
            fun getAll(): List<PocketOverTheCounter> {
                return listOf(
                    Etn,
                    REITs,
                    DepositoryReceipt,
                    LimitedPartnership,
                    Long2ETN,
                    Long3ETN,
                    StockOption,
                    ClosedEndFund,
                    SpecialShares,
                    SpecialSharesETD,
                    UnitTrust,
                    CommonStock,
                    OpenEndFund,
                    MiningRightsTrust,
                    Warrant,
                    Short1ETN,
                    Short3ETN,
                    NewYorkRegisteredShares
                )
            }

            fun valueOf(subType: Int): PocketOverTheCounter? {
                return getAll().find {
                    it.subType == subType
                }
            }
        }
    }

    /**
     * 口袋自編
     */
    sealed class PocketSelfEdit(
        override val type: Int = 42,
        override val subType: Int
    ) : MarketTypeV2(type, subType) {

        /**
         * 普通股
         */
        object CommonStock : PocketSelfEdit(subType = 1)

        companion object {
            fun getAll(): List<PocketSelfEdit> {
                return listOf(
                    CommonStock
                )
            }

            fun valueOf(subType: Int): PocketSelfEdit? {
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

            fun valueOf(subType: Int): InternationalIndex? {
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
                    addAll(IndexCompilation.getAll())
                    addAll(TseInnovation.getAll())
                    addAll(OtcStrategy.getAll())
                    addAll(TseWarrant.getAll())
                    addAll(OtcWarrant.getAll())
                    addAll(Emerging.getAll())
                    addAll(UsaStock.getAll())
                    addAll(InternationalIndex.getAll())
                    addAll(PocketOverTheCounter.getAll())
                    addAll(PocketSelfEdit.getAll())
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
