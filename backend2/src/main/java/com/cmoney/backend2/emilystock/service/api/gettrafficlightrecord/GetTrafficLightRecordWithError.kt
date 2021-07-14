package com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetTrafficLightRecordWithError(
    @SerializedName("Response")
    val response: List<TrafficLightRecord?>?
) : IWithError<GetTrafficLightRecord>, CMoneyError() {

    override fun toRealResponse(): GetTrafficLightRecord {
        return GetTrafficLightRecord(response = response)
    }
}