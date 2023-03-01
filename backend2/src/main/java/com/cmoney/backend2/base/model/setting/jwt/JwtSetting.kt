package com.cmoney.backend2.base.model.setting.jwt

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken

/**
 * Identity(JWT)登入所需參數
 */
interface JwtSetting {
    fun getAccessToken(): AccessToken

    fun setAccessToken(accessToken: AccessToken)

    fun getIdentityToken(): IdentityToken

    fun setIdentityToken(identityToken: IdentityToken)

    fun getRefreshToken(): String

    fun setRefreshToken(refreshToken: String)

    /**
     * 清除Jwt設定
     */
    fun clearJwtSetting()
}