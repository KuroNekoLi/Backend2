package com.cmoney.backend2.activity

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.Setting

/**
 * Fake [Setting] object for testing.
 */
class FakeSetting : Setting {
    override var domainUrl: String = ""
    override var appId: Int = -1
    override var clientId: String = ""
    override var appVersionCode: Int = -1
    override var appVersion: String = ""
    override val manufacturer: String = ""
    override val model: String = ""
    override val osVersion: String = ""
    override var platform: Platform = Platform.Android
    override var accessToken: AccessToken = AccessToken("")
    override var identityToken: IdentityToken = IdentityToken("")
    override var refreshToken: String = ""
    override fun clear() {
        accessToken = AccessToken("")
        identityToken = IdentityToken("")
        refreshToken = ""
    }
}