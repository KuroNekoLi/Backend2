package com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class AskStockTendencyAmountResponseWithError(
    @SerializedName("Amount")
    val amount: Int?
): CMoneyError(), IWithError<AskStockTendencyAmountResponse> {
    override fun toRealResponse(): AskStockTendencyAmountResponse {
        return AskStockTendencyAmountResponse(amount)
    }

}