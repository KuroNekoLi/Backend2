package com.cmoney.backend2.chat.service.api.variable

import com.google.gson.annotations.SerializedName

data class Properties(
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Name")
    val name: String?
)