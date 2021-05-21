package com.cmoney.backend2.customgroup.service.api.updatecustomlist

import com.google.gson.annotations.SerializedName

data class UpdateCustomListComplete(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)