package com.cmoney.backend2.base.model.setting.jwt

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken

/**
 * Identity(JWT)登入所需參數
 */
interface JwtSetting {

    /**
     * 取得Jwt的AccessToken
     */
    fun getAccessToken(): AccessToken

    /**
     * 設定Jwt的AccessToken
     */
    fun setAccessToken(accessToken: AccessToken)

    /**
     * 取得Jwt的IdentityToken
     */
    fun getIdentityToken(): IdentityToken

    /**
     * 設定Jwt的IdentityToken
     */
    fun setIdentityToken(identityToken: IdentityToken)

    /**
     * 取得Jwt的RefreshToken
     */
    fun getRefreshToken(): String

    /**
     * 設定Jwt的RefreshToken
     */
    fun setRefreshToken(refreshToken: String)

    /**
     * 清除Jwt設定
     */
    fun clearJwtSetting()
}