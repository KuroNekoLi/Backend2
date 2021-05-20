package com.cmoney.backend2.chat.service.api.variable

import com.google.gson.annotations.SerializedName

data class RuleSet(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("properties")
    val properties: Properties?,
    @SerializedName("rules")
    val rules: List<Rule?>?
)