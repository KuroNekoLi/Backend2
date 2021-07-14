package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class Questionnaire(
    @SerializedName("Id")
    val id: Long?,
    @SerializedName("Questions")
    val questions: List<Question?>?
)