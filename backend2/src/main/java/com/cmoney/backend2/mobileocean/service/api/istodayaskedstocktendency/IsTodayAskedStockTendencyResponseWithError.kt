package com.cmoney.backend2.mobileocean.service.api.istodayaskedstocktendency

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class IsTodayAskedStockTendencyResponseWithError(
    @SerializedName("IsAsked")
    val isAsked: Boolean?
) : CMoneyError(), IWithError<IsTodayAskedStockTendencyResponse> {

    override fun toRealResponse(): IsTodayAskedStockTendencyResponse {
        return IsTodayAskedStockTendencyResponse(isAsked)
    }
}