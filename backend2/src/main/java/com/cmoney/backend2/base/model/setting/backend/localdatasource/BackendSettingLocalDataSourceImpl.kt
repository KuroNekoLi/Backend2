package com.cmoney.backend2.base.model.setting.backend.localdatasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class BackendSettingLocalDataSourceImpl(
    context: Context
) : BackendSettingLocalDataSource {

    private val sharedPreferences: SharedPreferences

    init {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        sharedPreferences = EncryptedSharedPreferences.create(
            FILE_NAME,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun getDomainUrl(): String {
        return sharedPreferences.getString(DOMAIN_URL, "")!!
    }

    override fun setDomainUrl(domainUrl: String) {
        sharedPreferences.edit {
            putString(DOMAIN_URL, domainUrl)
        }
    }

    override fun getAppId(): Int {
        return sharedPreferences.getInt(APP_ID, 0)
    }

    override fun setAppId(appId: Int) {
        sharedPreferences.edit {
            putInt(APP_ID, appId)
        }
    }

    override fun getClientId(): String {
        return sharedPreferences.getString(CLIENT_ID, "")!!
    }

    override fun setClientId(clientId: String) {
        sharedPreferences.edit {
            putString(CLIENT_ID, clientId)
        }
    }

    override fun getPlatform(): Int {
        return sharedPreferences.getInt(PLATFORM, 0)
    }

    override fun setPlatform(platform: Int) {
        sharedPreferences.edit {
            putInt(PLATFORM, platform)
        }
    }

    companion object {
        private const val FILE_NAME = "cm_backend_setting_sp"
        private const val DOMAIN_URL = "domain_url"
        private const val APP_ID = "app_id"
        private const val CLIENT_ID = "client_id"
        private const val PLATFORM = "platform"
    }
}