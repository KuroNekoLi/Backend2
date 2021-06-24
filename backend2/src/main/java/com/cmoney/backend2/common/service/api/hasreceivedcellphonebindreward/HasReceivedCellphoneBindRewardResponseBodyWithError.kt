package com.cmoney.backend2.common.service.api.hasreceivedcellphonebindreward

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class HasReceivedCellphoneBindRewardResponseBodyWithError(
    /**
     * 是否已領過獎勵
     */
    @SerializedName("HasReceived")
    val hasReceived: Boolean?
) : CMoneyError(), IWithError<HasReceivedCellphoneBindRewardResponseBody> {
    override fun toRealResponse(): HasReceivedCellphoneBindRewardResponseBody {
        return HasReceivedCellphoneBindRewardResponseBody(hasReceived)
    }
}