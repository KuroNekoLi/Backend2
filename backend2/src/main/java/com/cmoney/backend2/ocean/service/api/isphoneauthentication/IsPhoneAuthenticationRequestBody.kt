package com.cmoney.backend2.ocean.service.api.isphoneauthentication

import com.google.gson.annotations.SerializedName

data class IsPhoneAuthenticationRequestBody(
    @SerializedName("MemberIdList")
    val memberIdList : List<Long>
)
