package com.cmoney.backend2.mobileocean.service.api.updatechanneldescription

import com.google.gson.annotations.SerializedName

data class UpdateChannelIdDescriptionResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)