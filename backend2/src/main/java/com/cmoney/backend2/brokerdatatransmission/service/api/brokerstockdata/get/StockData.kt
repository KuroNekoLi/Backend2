package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.google.gson.annotations.SerializedName

/**
 * 庫存資料
 *
 * @property stockId 股票代號
 * @property stockInfos 持有股票紀錄
 */
data class StockData(
    @SerializedName("stockID")
    val stockId: String?,
    @SerializedName("stockInfos")
    val stockInfos: List<StockInfo>?
)
