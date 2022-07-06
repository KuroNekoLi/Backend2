package com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetStocksInIndexResponseBodyWithError(
    @SerializedName("Stocks")
    val stocks: List<Stock>?,
) : CMoneyError(), IWithError<GetStocksInIndexResponseBody> {
    override fun toRealResponse(): GetStocksInIndexResponseBody {
        return GetStocksInIndexResponseBody(stocks = stocks)
    }
}