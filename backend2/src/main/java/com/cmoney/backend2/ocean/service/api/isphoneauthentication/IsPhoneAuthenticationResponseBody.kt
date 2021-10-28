package com.cmoney.backend2.ocean.service.api.isphoneauthentication


import com.google.gson.annotations.SerializedName

/**
 * 是否有手機認證
 *
 * @property data 帳號與其手機是否認證的訊息
 */
data class IsPhoneAuthenticationResponseBody(
    @SerializedName("Data")
    val data: List<PhoneAuthInfo>?
)