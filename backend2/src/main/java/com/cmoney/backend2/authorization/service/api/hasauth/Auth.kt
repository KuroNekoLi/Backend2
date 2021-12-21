package com.cmoney.backend2.authorization.service.api.hasauth


import com.google.gson.annotations.SerializedName

/**
 * 是否有權限之回傳
 *
 * @property hasAuthorization 是否有權限
 * @property serverTime 伺服器時間，utc時間，毫秒
 */
data class Auth(
    @SerializedName("hasAuthorization")
    val hasAuthorization: Boolean?,
    @SerializedName("serverTime")
    val serverTime: Long?
)