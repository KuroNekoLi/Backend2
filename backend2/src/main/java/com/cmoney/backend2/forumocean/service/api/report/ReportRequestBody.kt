package com.cmoney.backend2.forumocean.service.api.report

import com.google.gson.annotations.SerializedName

data class ReportRequestBody(
    @SerializedName("reason")
    val reason : Int? = null,
)
