package com.cmoney.backend2.note_extension

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.Setting

class TestSetting : Setting {
    override var domainUrl: String = ""
    override var appId: Int = -1
    override var clientId: String = ""
    override var appVersionCode: Int = -1
    override var appVersion: String = ""
    override var manufacturer = ""
    override var model = ""
    override var osVersion = ""
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