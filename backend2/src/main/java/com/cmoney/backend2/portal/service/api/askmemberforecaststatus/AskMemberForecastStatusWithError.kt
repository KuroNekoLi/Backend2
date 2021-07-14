package com.cmoney.backend2.portal.service.api.askmemberforecaststatus

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.portal.service.api.GuessBullBearStatus
import com.google.gson.annotations.SerializedName

data class AskMemberForecastStatusWithError(
    @SerializedName("Status")
    val status: GuessBullBearStatus?
) : CMoneyError(),IWithError<AskMemberForecastStatus> {
    override fun toRealResponse(): AskMemberForecastStatus {
        return AskMemberForecastStatus(
            status
        )
    }
}