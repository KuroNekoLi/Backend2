package com.cmoney.backend2.ocean.service.api.uploadchannelimage

import com.google.gson.annotations.SerializedName

data class UploadChannelImageResponseBody (
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
)