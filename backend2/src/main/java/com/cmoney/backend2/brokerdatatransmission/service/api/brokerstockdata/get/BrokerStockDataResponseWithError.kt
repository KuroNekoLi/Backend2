package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.StockData
import com.google.gson.annotations.SerializedName

class BrokerStockDataResponseWithError(
    @SerializedName("brokerId")
    val brokerId: String?,
    @SerializedName("brokerShortName")
    val brokerShortName: String?,
    @SerializedName("subBrokerId")
    val subBrokerId: String?,
    @SerializedName("updateTimeOfUnixTimeSeconds")
    val updateTimeOfUnixTimeSeconds: Long?,
    @SerializedName("inStockData")
    val inStockData: List<StockData>?
) : CMoneyError(), IWithError<BrokerStockDataResponse> {
    override fun toRealResponse(): BrokerStockDataResponse {
        return BrokerStockDataResponse(
            brokerId,
            brokerShortName,
            subBrokerId,
            updateTimeOfUnixTimeSeconds,
            inStockData,
        )
    }
}
