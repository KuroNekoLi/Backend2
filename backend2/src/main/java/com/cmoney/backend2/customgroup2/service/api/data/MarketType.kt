package com.cmoney.backend2.customgroup2.service.api.data

/**
 * 市場類型
 *
 * @property value 識別值
 * @property name 市場名稱
 * @property types 子類型
 * @constructor Create empty Market type
 */
sealed class MarketType(
    open val value: Int,
    open val name: String,
    open val types: List<SubType>
) {

    /**
     * 上市
     */
    data class Tse(
        override val value: Int = 2,
        override val name: String = "上市",
        override val types: List<TseSubType> = TseSubType.values()
    ) : MarketType(value, name, types) {

        /**
         * 上市子類型
         */
        sealed class TseSubType(override val value: Int, override val name: String) : SubType {

            /**
             * 國內/外指數股票型ETF
             */
            object DomesticAndForeignIndexStockEtf : TseSubType(
                160,
                "國內/外指數股票型ETF"
            )

            /**
             * 加掛式指數股票型ETF
             */
            object AddOnIndexStockEtf : TseSubType(
                195,
                "加掛式指數股票型ETF"
            )

            /**
             * 槓桿指數股票型ETF
             */
            object LeveragedIndexStockEtf : TseSubType(
                196,
                "槓桿指數股票型ETF"
            )

            /**
             * 反向指數股票型ETF
             */
            object InverseIndexStockEtf : TseSubType(
                198,
                "反向指數股票型ETF"
            )

            /**
             * 期信指數股票型ETF
             */
            object FuturesIndexStockEtf : TseSubType(
                200,
                "期信指數股票型ETF"
            )

            /**
             * 債券指數股票型ETF
             */
            object BondIndexStockEtf : TseSubType(
                206,
                "債券指數股票型ETF"
            )

            /**
             * 加掛式債券指數型ETF
             */
            object AddOnBondIndexEtf : TseSubType(
                207,
                "加掛式債券指數型ETF"
            )

            /**
             * 股票
             */
            object Stock : TseSubType(
                90,
                "股票"
            )

            /**
             * 存託憑證
             */
            object DepositoryReceipt : TseSubType(
                158,
                "存託憑證"
            )

            companion object {
                fun values(): List<TseSubType> {
                    return listOf(
                        DomesticAndForeignIndexStockEtf,
                        AddOnIndexStockEtf,
                        LeveragedIndexStockEtf,
                        InverseIndexStockEtf,
                        FuturesIndexStockEtf,
                        BondIndexStockEtf,
                        AddOnBondIndexEtf,
                        Stock,
                        DepositoryReceipt
                    )
                }
            }
        }
    }

    /**
     * 上櫃
     */
    data class Otc(
        override val value: Int = 3,
        override val name: String = "上櫃",
        override val types: List<OtcSubType> = OtcSubType.values()
    ) : MarketType(value, name, types) {

        /**
         * 上櫃子類型
         */
        sealed class OtcSubType(override val value: Int, override val name: String) : SubType {

            /**
             * 國內/外指數股票型ETF
             */
            object DomesticAndForeignIndexStockEtf : OtcSubType(
                160,
                "國內/外指數股票型ETF"
            )

            /**
             * 債券指數股票型ETF
             */
            object BondIndexStockEtf : OtcSubType(
                206,
                "債券指數股票型ETF"
            )

            /**
             * 股票
             */
            object Stock : OtcSubType(
                90,
                "股票"
            )

            /**
             * 存託憑證
             */
            object DepositoryReceipt : OtcSubType(
                158,
                "存託憑證"
            )

            companion object {
                fun values(): List<OtcSubType> {
                    return listOf(
                        DomesticAndForeignIndexStockEtf,
                        BondIndexStockEtf,
                        Stock,
                        DepositoryReceipt
                    )
                }
            }
        }
    }

    /**
     * 興櫃
     */
    data class Emerging(
        override val value: Int = 6,
        override val name: String = "興櫃",
        override val types: List<EmergingSubType> = EmergingSubType.values()
    ) : MarketType(value, name, types) {

        /**
         * 興櫃子類型
         */
        sealed class EmergingSubType(override val value: Int, override val name: String) : SubType {

            /**
             * 股票
             */
            object Stock : EmergingSubType(
                90,
                "股票"
            )

            companion object {
                fun values(): List<EmergingSubType> {
                    return listOf(
                        Stock
                    )
                }
            }
        }
    }

    /**
     * 美股
     */
    data class UsaStock(
        override val value: Int = 40,
        override val name: String = "美股",
        override val types: List<UsaStockSubType> = UsaStockSubType.values()
    ) : MarketType(value, name, types) {

        /**
         * 美股子類型
         */
        sealed class UsaStockSubType(override val value: Int, override val name: String) : SubType {

            /**
             * 普通股
             */
            object CommonStock : UsaStockSubType(
                1,
                "普通股"
            )

            /**
             * 美國存託憑證
             */
            object AmericanDepositaryReceipt : UsaStockSubType(
                2,
                "美國存託憑證"
            )

            /**
             * 附認股權證普通股
             */
            object OrdinarySharesWithWarrants : UsaStockSubType(
                7,
                "附認股權證普通股"
            )

            /**
             * 股本權證
             */
            object EquityWarrants : UsaStockSubType(
                3,
                "股本權證"
            )

            /**
             * 特別股
             */
            object SpecialShares : UsaStockSubType(
                4,
                "特別股"
            )

            /**
             * 不動產投資信託
             */
            object RealEstateInvestmentTrust : UsaStockSubType(
                5,
                "不動產投資信託"
            )

            /**
             * 業主有限合夥股
             */
            object OwnersLimitedPartnershipShares : UsaStockSubType(
                6,
                "業主有限合夥股"
            )

            /**
             * 封閉型基金
             */
            object ClosedEndFund : UsaStockSubType(
                8,
                "封閉型基金"
            )

            /**
             * 認股權
             */
            object StockOption : UsaStockSubType(
                9,
                "認股權"
            )

            /**
             * 紐約註冊股份
             */
            object NewYorkRegisteredShares : UsaStockSubType(
                10,
                "紐約註冊股份"
            )

            /**
             * 追蹤股
             */
            object TrackingStock : UsaStockSubType(
                11,
                "追蹤股"
            )

            /**
             * 有限合夥股
             */
            object LimitedPartnershipShares : UsaStockSubType(
                12,
                "有限合夥股"
            )

            /**
             * 權利金信託
             */
            object RoyaltyTrust : UsaStockSubType(
                13,
                "權利金信託"
            )

            /**
             * Etf
             */
            object Etf : UsaStockSubType(
                14,
                "ETF"
            )

            companion object {
                fun values(): List<UsaStockSubType> {
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
            }
        }
    }

    /**
     * 國際指數
     */
    data class InternationalIndex(
        override val value: Int = 70,
        override val name: String = "國際指數",
        override val types: List<InternationalIndexSubType> = InternationalIndexSubType.values()
    ) : MarketType(value, name, types) {

        /**
         * 國際指數子類型
         */
        sealed class InternationalIndexSubType(
            override val value: Int,
            override val name: String
        ) : SubType {

            /**
             * 美國
             */
            object UnitedState: InternationalIndexSubType(
                840,
                "美國"
            )

            /**
             * 法國
             */
            object France: InternationalIndexSubType(
                250,
                "法國"
            )

            /**
             * 英國
             */
            object UnitedKingdom: InternationalIndexSubType(
                826,
                "英國"
            )

            /**
             * 德國
             */
            object Germany: InternationalIndexSubType(
                276,
                "德國"
            )

            /**
             * 香港
             */
            object Hongkong: InternationalIndexSubType(
                344,
                "香港"
            )

            /**
             * 韓國
             */
            object SouthKorea: InternationalIndexSubType(
                410,
                "韓國"
            )

            /**
             * 日本
             */
            object Japan: InternationalIndexSubType(
                392,
                "日本"
            )

            /**
             * 俄羅斯
             */
            object Russia: InternationalIndexSubType(
                643,
                "俄羅斯"
            )

            /**
             * 中國
             */
            object China: InternationalIndexSubType(
                156,
                "中國"
            )

            /**
             * 臺灣
             */
            object Taiwan: InternationalIndexSubType(
                158,
                "臺灣"
            )

            companion object {
                fun values(): List<InternationalIndexSubType> {
                    return listOf(
                        UnitedState,
                        France,
                        UnitedKingdom,
                        Germany,
                        SouthKorea,
                        Japan,
                        Russia,
                        China,
                        Taiwan
                    )
                }
            }
        }
    }

    /**
     * 子類型
     *
     * @property value 識別值
     * @property name 中文名稱
     */
    interface SubType {
        val value: Int
        val name: String
    }

    companion object {
        fun values(): List<MarketType> {
            return listOf(
                Tse(),
                Otc(),
                Emerging(),
                UsaStock(),
                InternationalIndex()
            )
        }

        fun fromInt(value: Int): MarketType? {
            for (type in values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }
    }
}
