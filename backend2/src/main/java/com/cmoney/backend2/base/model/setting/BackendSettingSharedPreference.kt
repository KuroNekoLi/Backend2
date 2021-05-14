package com.cmoney.backend2.base.model.setting

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * 沒加密過的SharedPreference．
 */
class BackendSettingSharedPreference(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val TAG = "backend_setting_sp"
        private const val DOMAIN_URL = "domain_url"
        private const val APP_ID = "app_id"
        private const val CLIENT_ID = "client_id"
        private const val APP_VERSION_CODE = "app_version_code"
        private const val APP_VERSION = "app_version"
        private const val PLATFORM = "platform"
        private const val ACCESS_TOKEN = "access_token"
        private const val IDENTITY_TOKEN = "identity_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val DEFAULT_DOMAIN_URL = "https://www.cmoney.tw/"
    }

    var domainUrl: String
        get() = sharedPreferences.getString(
            DOMAIN_URL,
            DEFAULT_DOMAIN_URL
        )!!
        set(value) {
            sharedPreferences.edit {
                putString(DOMAIN_URL, value)
            }
        }

    var appId: Int
        get() = sharedPreferences.getInt(APP_ID, -1)
        set(value) {
            sharedPreferences.edit {
                putInt(APP_ID, value)
            }
        }

    var clientId: String
        get() = sharedPreferences.getString(CLIENT_ID, "")!!
        set(value) {
            sharedPreferences.edit {
                putString(CLIENT_ID, value)
            }
        }

    var appVersionCode: Int
        get() = sharedPreferences.getInt(APP_VERSION_CODE, -1)
        set(value) {
            sharedPreferences.edit {
                putInt(APP_VERSION_CODE, value)
            }
        }

    var appVersion: String
        get() = sharedPreferences.getString(APP_VERSION, "")!!
        set(value) {
            sharedPreferences.edit {
                putString(APP_VERSION, value)
            }
        }

    var platform: String
        get() = sharedPreferences.getString(PLATFORM, "")!!
        set(value) {
            sharedPreferences.edit {
                putString(PLATFORM, value)
            }
        }

    var accessToken: String
        get() = sharedPreferences.getString(ACCESS_TOKEN, "")!!
        set(value) {
            sharedPreferences.edit {
                putString(ACCESS_TOKEN, value)
            }
        }

    var identityToken: String
        get() = sharedPreferences.getString(IDENTITY_TOKEN, "")!!
        set(value) {
            sharedPreferences.edit {
                putString(IDENTITY_TOKEN, value)
            }
        }

    var refreshToken: String
        get() = sharedPreferences.getString(REFRESH_TOKEN, "")!!
        set(value) {
            sharedPreferences.edit {
                putString(REFRESH_TOKEN, value)
            }
        }

    fun clear() {
        sharedPreferences.edit {
            clear()
        }
    }
}