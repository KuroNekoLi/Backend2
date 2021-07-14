package com.cmoney.backend2.common.service.api.getdailyheadline


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class HeadlineResponse(
    @SerializedName("News")
    val newsList: List<News>?
) : CMoneyError()