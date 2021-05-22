package com.cmoney.backend2.chipk.service.api.getOfficialStockPickData

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class OfficialStockInfoWithError(

    @SerializedName("ReplaceSymbol")
    val replaceSymbol: String = "",

    @SerializedName("Description")
    val description: String = "",

    @SerializedName("StockList")
    val stockList: List<OfficialStock> = emptyList()
) : CMoneyError(),
    IWithError<OfficialStockInfo> {
    override fun toRealResponse(): OfficialStockInfo {
        return OfficialStockInfo(
            replaceSymbol = replaceSymbol,
            description = description,
            stockList = stockList
        )
    }
}
