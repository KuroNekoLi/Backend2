package com.cmoney.backend2.chat.service.api.variable

import com.google.gson.annotations.SerializedName

data class RuleProperties(
    @SerializedName("Allow")
    val allow: Boolean?,
    @SerializedName("Fields")
    val fields: List<String?>?,
    @SerializedName("Range")
    val range: String?
)