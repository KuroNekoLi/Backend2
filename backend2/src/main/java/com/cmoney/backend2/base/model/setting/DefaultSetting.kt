package com.cmoney.backend2.base.model.setting

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken

class DefaultSetting(
    private val backendSettingSharedPreference: BackendSettingSharedPreference
) : Setting {
    override var domainUrl: String = backendSettingSharedPreference.domainUrl
        set(value) {
            backendSettingSharedPreference.domainUrl = value
            field = value
        }
    override var appId: Int = -1
        set(value) {
            backendSettingSharedPreference.appId = value
            field = value
        }
    override var clientId: String = ""
        set(value) {
            backendSettingSharedPreference.clientId = value
            field = value
        }
    override var appVersionCode: Int = -1
        set(value) {
            backendSettingSharedPreference.appVersionCode = value
            field = value
        }
    override var appVersion: String = "0.0.0"
        set(value) {
            backendSettingSharedPreference.appVersion = value
            field = value
        }
    override val manufacturer: String = android.os.Build.MANUFACTURER
    override val model: String = android.os.Build.MODEL
    override val osVersion: String = android.os.Build.VERSION.SDK_INT.toString()
    override var platform: Platform = Platform.Android
        set(value) {
            backendSettingSharedPreference.platform = value.code.toString()
            field = value
        }
    override var accessToken: AccessToken = AccessToken(backendSettingSharedPreference.accessToken)
        set(value) {
            backendSettingSharedPreference.accessToken = value.originContent
            field = value
        }
    override var identityToken: IdentityToken =
        IdentityToken(backendSettingSharedPreference.identityToken)
        set(value) {
            backendSettingSharedPreference.identityToken = value.originContent
            field = value
        }
    override var refreshToken: String = backendSettingSharedPreference.refreshToken
        set(value) {
            backendSettingSharedPreference.refreshToken = value
            field = value
        }

    override fun clear() {
        accessToken = AccessToken()
        identityToken = IdentityToken()
        refreshToken = ""
    }
}