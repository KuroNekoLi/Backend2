package com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class AddAskStockTendencyLogResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(), IWithError<AddAskStockTendencyLogResponse> {

    override fun toRealResponse(): AddAskStockTendencyLogResponse {
        return AddAskStockTendencyLogResponse(isSuccess)
    }
}