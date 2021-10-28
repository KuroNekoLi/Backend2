package com.cmoney.backend2.ocean.service.api.isphoneauthentication


import com.google.gson.annotations.SerializedName

/**
 * 手機認證資訊
 *
 * @property hadPhoneAuthentication 是否手機認證
 * @property memberId 會員id
 */
data class PhoneAuthInfo(
    @SerializedName("HadPhoneAuthentication")
    val hadPhoneAuthentication: Boolean?,
    @SerializedName("MemberId")
    val memberId: Long?
)