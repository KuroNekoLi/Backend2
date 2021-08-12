package com.cmoney.backend2.chipk.service.api.internationalkchart


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class TickInfoSetWithError(
    @SerializedName("TickInfoSet")
    val tickInfoSet: List<TickInfo?>?
): CMoneyError(), IWithError<TickInfoSet> {

    override fun toRealResponse(): TickInfoSet {
        return TickInfoSet(tickInfoSet)
    }
}