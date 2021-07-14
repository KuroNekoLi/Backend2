package com.cmoney.backend2.realtimeaftermarket.service.api.getafterhourstime

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class AfterHoursTimeWithError(
        @SerializedName("Date")
        val date: String
): IWithError<AfterHoursTime>, CMoneyError() {

        override fun toRealResponse(): AfterHoursTime {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val date = simpleDateFormat.parse(date) ?: throw EmptyBodyException()
                return AfterHoursTime(date)
        }
}