package com.cmoney.backend2.ocean.service.api.changememberstatus

import com.google.gson.annotations.SerializedName

data class ChangeMemberStatusResponseBody(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
)