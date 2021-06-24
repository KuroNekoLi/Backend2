package com.cmoney.backend2.ocean.service.api.checkhasevaluated


import com.google.gson.annotations.SerializedName

data class CheckHasEvaluatedResponseBody(
    @SerializedName("HasEvaluated")
    val hasEvaluated: Boolean?
)