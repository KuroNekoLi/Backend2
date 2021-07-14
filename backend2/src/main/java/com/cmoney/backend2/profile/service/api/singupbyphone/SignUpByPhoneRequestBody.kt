package com.cmoney.backend2.profile.service.api.singupbyphone

import com.google.gson.annotations.SerializedName

data class SignUpByPhoneRequestBody(
   @SerializedName("Cellphone")
   val cellphone: String
)