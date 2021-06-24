package com.cmoney.backend2.ocean.service.api.variable
import com.google.gson.annotations.SerializedName

data class SuccessResult(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)