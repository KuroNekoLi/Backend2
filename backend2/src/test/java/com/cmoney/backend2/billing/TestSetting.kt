package com.cmoney.backend2.billing

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.Setting

class TestSetting: Setting {
    override var domainUrl: String = ""
    override var appId: Int = -1
    override var clientId: String = ""
    override var appVersionCode: Int = -1
    override var appVersion: String = ""
    override var platform: Platform = Platform.Android
    override var accessToken: AccessToken = AccessToken("")
    override var identityToken: IdentityToken = IdentityToken("")
    override val manufacturer: String
        get() = ""
    override val model: String
        get() = ""
    override val osVersion: String
        get() = ""
    override var refreshToken: String = ""
    override fun clear() {
        accessToken = AccessToken("")
        identityToken = IdentityToken("")
        refreshToken = ""
    }
}