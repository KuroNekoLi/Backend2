package com.cmoney.backend2.additioninformationrevisit.service.api.request

import com.google.gson.annotations.SerializedName

data class ProcessStep(
    @SerializedName("ProcessType")
    val type: String,
    @SerializedName("ParameterJson")
    val json: String
)
