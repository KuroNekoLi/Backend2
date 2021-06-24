package com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class PopularStockCollectionWithError (
    @SerializedName("Stocks")
    val stocks: List<PopularStock?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
):CMoneyError(), IWithError<PopularStockCollection>{
    override fun toRealResponse(): PopularStockCollection {
        return PopularStockCollection(stocks, updatedInSeconds)
    }
}