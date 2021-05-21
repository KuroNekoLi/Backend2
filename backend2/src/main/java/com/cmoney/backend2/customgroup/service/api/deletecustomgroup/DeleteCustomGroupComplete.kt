package com.cmoney.backend2.customgroup.service.api.deletecustomgroup

import com.google.gson.annotations.SerializedName

data class DeleteCustomGroupComplete(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)