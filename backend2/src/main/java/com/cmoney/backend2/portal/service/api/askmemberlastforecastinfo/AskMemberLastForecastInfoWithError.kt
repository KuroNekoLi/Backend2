package com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class AskMemberLastForecastInfoWithError(
    @SerializedName("Result")
    val result: LastForecastInfo?
): CMoneyError(),IWithError<AskMemberLastForecastInfo> {
    override fun toRealResponse(): AskMemberLastForecastInfo {
        return AskMemberLastForecastInfo(
            result
        )
    }
}
