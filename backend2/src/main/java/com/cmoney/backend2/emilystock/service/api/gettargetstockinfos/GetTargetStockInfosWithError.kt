package com.cmoney.backend2.emilystock.service.api.gettargetstockinfos

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetTargetStockInfosWithError(
    @SerializedName("Response")
    val response: List<StockInfo?>?
) : IWithError<GetTargetStockInfos>, CMoneyError() {

    override fun toRealResponse(): GetTargetStockInfos {
        return GetTargetStockInfos(
            response = response
        )
    }
}