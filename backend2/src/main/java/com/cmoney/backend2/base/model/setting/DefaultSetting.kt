package com.cmoney.backend2.base.model.setting

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken

@Deprecated("使用GlobalBackend2Manager代替")
class DefaultSetting(
    private val manager: GlobalBackend2Manager
) : Setting {
    override var domainUrl: String
        get() = manager.getGlobalDomainUrl()
        set(value) {
            manager.setGlobalDomainUrl(value)
        }
    override var appId: Int
        get() = manager.getAppId()
        set(value) {
            manager.setAppId(value)
        }
    override var clientId: String
        get() = manager.getClientId()
        set(value) {
            manager.setClientId(value)
        }
    override val appVersionCode: Int = manager.getAppVersionCode().toInt()
    override val appVersion: String = manager.getAppVersionName()
    override val manufacturer: String = manager.getManufacturer()
    override val model: String = manager.getModel()
    override val osVersion: String = manager.getOsVersion()
    override var platform: Platform
        get() = manager.getPlatform()
        set(value) {
            manager.setPlatform(value)
        }
    override var accessToken: AccessToken
        get() = manager.getAccessToken()
        set(value) {
            manager.setAccessToken(value)
        }
    override var identityToken: IdentityToken
        get() = manager.getIdentityToken()
        set(value) {
            manager.setIdentityToken(value)
        }
    override var refreshToken: String
        get() = manager.getRefreshToken()
        set(value) {
            manager.setRefreshToken(value)
        }

    override fun clear() {
        manager.setAccessToken(AccessToken())
        manager.setIdentityToken(IdentityToken())
        manager.setRefreshToken("")
    }
}