package com.cmoney.backend2.chat.service.api.variable

import com.google.gson.annotations.SerializedName

data class Rule(
    @SerializedName("name")
    val name: String?,
    @SerializedName("properties")
    val properties: RuleProperties?,
    @SerializedName("role")
    val role: String?
)