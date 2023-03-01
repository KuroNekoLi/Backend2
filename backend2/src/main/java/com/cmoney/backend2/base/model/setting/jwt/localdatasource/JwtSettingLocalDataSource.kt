package com.cmoney.backend2.base.model.setting.jwt.localdatasource

interface JwtSettingLocalDataSource {
    fun getAccessToken(): String

    fun setAccessToken(accessToken: String)

    fun getIdentityToken(): String

    fun setIdentityToken(identityToken: String)

    fun getRefreshToken(): String

    fun setRefreshToken(refreshToken: String)
}