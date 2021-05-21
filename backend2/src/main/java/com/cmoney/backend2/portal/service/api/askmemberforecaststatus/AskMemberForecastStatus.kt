package com.cmoney.backend2.portal.service.api.askmemberforecaststatus
import com.cmoney.backend2.portal.service.api.GuessBullBearStatus
import com.google.gson.annotations.SerializedName


data class AskMemberForecastStatus(
    @SerializedName("Status")
    val status: GuessBullBearStatus?
)