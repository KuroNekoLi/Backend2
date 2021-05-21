package com.cmoney.backend2.realtimeaftermarket.service.api.getisintradeday

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetIsInTradeDayResponseBodyWithError(
    /**
     * 是否盤中
     */
    @SerializedName("IsIntraday")
    val isInTradeDay : Boolean?
) : CMoneyError(),IWithError<GetIsInTradeDayResponseBody> {
    override fun toRealResponse(): GetIsInTradeDayResponseBody {
        return GetIsInTradeDayResponseBody(isInTradeDay)
    }
}