package com.cmoney.backend2.authorization.service.api.hasauth


import com.google.gson.annotations.SerializedName

/**
 * 是否有權限之回傳
 *
 * @property hasAuthorization 是否有權限
 */
data class Auth(
    @SerializedName("hasAuthorization")
    val hasAuthorization: Boolean?
)