package com.cmoney.backend2.chipk.service.api.futuredaytradedtnodata

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class FutureDayTradeDtnoWithError(
    @SerializedName("Title")
    val title: List<String>?,
    @SerializedName("Data")
    val data: List<String>?
) : CMoneyError(),
    IWithError<FutureDayTradeDtnoData> {

    override fun toRealResponse(): FutureDayTradeDtnoData {
        return FutureDayTradeDtnoData(
            title = title,
            data = data
        )
    }
}