package com.cmoney.backend2.profile.service.api.isphoneexists

import com.google.gson.annotations.SerializedName

data class IsPhoneExistRequest(
    /**
     * Phone with country code. e.g. +886912345678
     */
    @SerializedName("Cellphone")
    val phone: String
)
