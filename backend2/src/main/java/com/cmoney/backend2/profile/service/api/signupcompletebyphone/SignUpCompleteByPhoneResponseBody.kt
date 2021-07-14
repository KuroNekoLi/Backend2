package com.cmoney.backend2.profile.service.api.signupcompletebyphone

import com.google.gson.annotations.SerializedName

data class SignUpCompleteByPhoneResponseBody(
   @SerializedName("account")
   val account: String,
   @SerializedName("password")
   val password: String
)