package com.cmoney.backend2.commonuse.service.api.query

import com.google.gson.annotations.SerializedName

data class QueryParam(
    @SerializedName("query")
    val query: String
)
