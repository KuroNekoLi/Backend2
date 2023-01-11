package com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate

import java.math.BigDecimal

/**
 * 建立委託單請求
 *
 * @property accountId 帳號編號
 * @property buySellType 買賣類型
 * @property commodityId 商品代號
 * @property subsistingType 存續種類
 * @property groupId 競技場編號
 * @property delegatePrice 委託價
 * @property delegateVolume 委託量(股)
 * @property marketUnit 市場交易單位
 * @property transactionType 交易類型
 *
 */
data class CreateDelegateRequest(
    val accountId: Long,
    val buySellType: BuySellType,
    val commodityId: String,
    val subsistingType: SubsistingType,
    val groupId: Long,
    val delegatePrice: BigDecimal,
    val delegateVolume: BigDecimal,
    val marketUnit: TradingMarketUnit,
    val transactionType: TransactionType
) {
    /**
     * 買賣類型
     */
    sealed class BuySellType(
        val code: Int
    ) {
        /**
         * 買
         */
        object Buy : BuySellType(code = 66)

        /**
         * 賣
         */
        object Sell : BuySellType(code = 83)
    }

    /**
     * 委託條件
     */
    sealed class SubsistingType(
        val code: Int
    ) {
        /**
         * 當日委託有效單
         */
        object Rod : SubsistingType(code = 82)

        /**
         * 立即成交否則取消
         */
        object Ioc : SubsistingType(code = 73)

        /**
         * 立即全部成交否則取消
         */
        object Fok : SubsistingType(code = 70)
    }

    /**
     * 交易類型
     */
    sealed class TransactionType(
        val code: Int
    ) {

        /**
         * 現股
         */
        object MoneyStock : TransactionType(code = 1)

        /**
         * 融資
         */
        object MarginPurchase : TransactionType(code = 2)

        /**
         * 融券
         */
        object ShortSale : TransactionType(code = 3)
    }

    /**
     * 交易市場單位
     */
    sealed class TradingMarketUnit(
        val code: Int
    ) {
        /**
         * 整股
         */
        object BoardLot: TradingMarketUnit(code = 1)
    }

}