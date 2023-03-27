package com.cmoney.backend2.base.model.setting.jwt

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.base.model.setting.jwt.localdatasource.JwtSettingLocalDataSource

class JwtSettingImpl(
    private val localDataSource: JwtSettingLocalDataSource
) : JwtSetting {
    private var accessToken: AccessToken = AccessToken(localDataSource.getAccessToken())
    private var identityToken: IdentityToken = IdentityToken(localDataSource.getIdentityToken())
    private var refreshToken: String = localDataSource.getRefreshToken()

    override fun getAccessToken(): AccessToken {
        return accessToken
    }

    override fun setAccessToken(accessToken: AccessToken) {
        this.accessToken = accessToken
        localDataSource.setAccessToken(accessToken.originContent)
    }

    override fun getIdentityToken(): IdentityToken {
        return identityToken
    }

    override fun setIdentityToken(identityToken: IdentityToken) {
        this.identityToken = identityToken
        localDataSource.setIdentityToken(identityToken.originContent)
    }

    override fun getRefreshToken(): String {
        return refreshToken
    }

    override fun setRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
        localDataSource.setRefreshToken(refreshToken)
    }

    override fun clearJwtSetting() {
        setAccessToken(AccessToken())
        setIdentityToken(IdentityToken())
        setRefreshToken("")
    }
}