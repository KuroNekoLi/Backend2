package com.cmoney.backend2.base.model.setting.jwt.localdatasource

/**
 * Jwt設定本地資料來源
 */
interface JwtSettingLocalDataSource {
    /**
     * 取得Jwt的AccessToken
     */
    fun getAccessToken(): String

    /**
     * 設定Jwt的AccessToken
     */
    fun setAccessToken(accessToken: String)

    /**
     * 取得Jwt的IdentityToken
     */
    fun getIdentityToken(): String

    /**
     * 設定Jwt的IdentityToken
     */
    fun setIdentityToken(identityToken: String)

    /**
     * 取得Jwt的RefreshToken
     */
    fun getRefreshToken(): String

    /**
     * 設定Jwt的RefreshToken
     */
    fun setRefreshToken(refreshToken: String)
}