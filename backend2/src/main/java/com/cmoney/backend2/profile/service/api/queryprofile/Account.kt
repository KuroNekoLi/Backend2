package com.cmoney.backend2.profile.service.api.queryprofile


import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("appleId")
    val appleId: String?,
    @SerializedName("cellphone")
    val cellphone: Cellphone?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("facebook")
    val facebook: Facebook?,
    @SerializedName("guestId")
    val guestId: String?
)