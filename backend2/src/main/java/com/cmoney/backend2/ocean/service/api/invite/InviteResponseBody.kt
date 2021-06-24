package com.cmoney.backend2.ocean.service.api.invite

import com.google.gson.annotations.SerializedName

data class InviteResponseBody (
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
)