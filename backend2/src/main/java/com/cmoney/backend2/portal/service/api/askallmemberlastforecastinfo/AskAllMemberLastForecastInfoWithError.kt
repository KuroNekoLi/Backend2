package com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class AskAllMemberLastForecastInfoWithError(
    @SerializedName("Result")
    val result: List<StockLastForecastInfo>?
) : CMoneyError(),IWithError<AskAllMemberLastForecastInfo> {
    override fun toRealResponse(): AskAllMemberLastForecastInfo {
        return AskAllMemberLastForecastInfo(
            result
        )
    }
}