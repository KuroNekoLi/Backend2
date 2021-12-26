package com.cmoney.backend2.profile.service.api.queryprofile


import com.google.gson.annotations.SerializedName

data class RawAccount(
    @SerializedName("appleId")
    val appleId: String?,
    @SerializedName("cellphone")
    val cellphone: RawCellphone?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("facebook")
    val facebook: RawFacebook?,
    @SerializedName("guestId")
    val guestId: String?
)