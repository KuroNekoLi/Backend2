package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.google.gson.annotations.SerializedName

/**
 * 持有股票
 *
 * @property tradeTypeValue 交易類型
 * @property amount 股數
 * @property tradeTotalCost 交易成本(平均成本 + 手續費) 若無手續費就不要計算
 * @property cashDividend 現金股利
 * @property stockDividend 股票股利
 */
data class ImageRecognitionStockInfo(
    @SerializedName("TradeType")
    internal val tradeTypeValue: Byte?,
    @SerializedName("Amount")
    val amount: Long?,
    @SerializedName("TradeTotalCost")
    val tradeTotalCost: Double?,
    @SerializedName("CashDividend")
    val cashDividend: Double?,
    @SerializedName("StockDividend")
    val stockDividend: Double?,
) {
    val tradeType get() = tradeTypeValue?.let { TradeType.fromValue(tradeTypeValue) }
}
