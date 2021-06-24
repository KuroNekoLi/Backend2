package com.cmoney.backend2.common.service.api.getconfig

import com.google.gson.annotations.SerializedName

enum class AppStatus {
    @SerializedName("1")
    OK,
    @SerializedName("2")
    ERROR
}