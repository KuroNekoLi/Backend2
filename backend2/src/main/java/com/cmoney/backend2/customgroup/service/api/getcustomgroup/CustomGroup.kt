package com.cmoney.backend2.customgroup.service.api.getcustomgroup

import com.google.gson.annotations.SerializedName

data class CustomGroup(
    @SerializedName("Group")
    val group: List<SingleGroup>?
)