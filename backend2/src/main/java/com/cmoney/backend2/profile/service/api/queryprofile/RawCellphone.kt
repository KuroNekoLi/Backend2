package com.cmoney.backend2.profile.service.api.queryprofile


import com.google.gson.annotations.SerializedName

data class RawCellphone(
    @SerializedName("code")
    val code: String?,
    @SerializedName("number")
    val number: String?
)