package com.cmoney.backend2.ocean.service.api.leaveclub

import com.google.gson.annotations.SerializedName

data class LeaveClubResponseBody (
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
)