package com.cmoney.backend2.customgroup.service.api.updatecustomgroupname

import com.google.gson.annotations.SerializedName

data class UpdateCustomGroupNameComplete(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)