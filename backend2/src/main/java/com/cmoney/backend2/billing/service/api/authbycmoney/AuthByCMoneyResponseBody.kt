package com.cmoney.backend2.billing.service.api.authbycmoney

import com.google.gson.annotations.SerializedName

data class AuthByCMoneyResponseBody(
    @SerializedName("autorenewalingByCmoney", alternate = ["AutorenewalingByCmoney"])
    val isAuth: Boolean?
)
