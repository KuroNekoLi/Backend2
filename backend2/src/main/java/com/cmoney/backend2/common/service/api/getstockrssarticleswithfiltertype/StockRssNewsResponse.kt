package com.cmoney.backend2.common.service.api.getstockrssarticleswithfiltertype


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class StockRssNewsResponse(
    @SerializedName("News")
    val stockNewsList: List<StockNews>?
) : CMoneyError()