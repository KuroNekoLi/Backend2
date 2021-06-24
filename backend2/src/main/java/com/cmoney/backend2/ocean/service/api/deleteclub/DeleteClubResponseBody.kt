package com.cmoney.backend2.ocean.service.api.deleteclub

import com.google.gson.annotations.SerializedName

data class DeleteClubResponseBody (
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
)