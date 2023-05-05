package com.cmoney.backend2.base.model.setting.jwt.localdatasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class JwtSettingLocalDataSourceImpl(
    context: Context
) : JwtSettingLocalDataSource {

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

    override fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, "")!!
    }

    override fun setAccessToken(accessToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
        }
    }

    override fun getIdentityToken(): String {
        return sharedPreferences.getString(IDENTITY_TOKEN, "")!!
    }

    override fun setIdentityToken(identityToken: String) {
        sharedPreferences.edit {
            putString(IDENTITY_TOKEN, identityToken)
        }
    }

    override fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, "")!!
    }

    override fun setRefreshToken(refreshToken: String) {
        sharedPreferences.edit {
            putString(REFRESH_TOKEN, refreshToken)
        }
    }

    companion object {
        private const val FILE_NAME = "cm_identity_sp"
        private const val ACCESS_TOKEN = "access_token"
        private const val IDENTITY_TOKEN = "identity_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}