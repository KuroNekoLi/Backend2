package com.cmoney.backend2.customgroup.service.api.updatecustomgrouporder

import com.google.gson.annotations.SerializedName

data class UpdateCustomGroupOrderComplete(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)