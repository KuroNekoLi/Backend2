package com.cmoney.backend2.sample.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.identityprovider.service.IdentityProviderWeb
import com.cmoney.backend2.sample.model.SingleLiveEvent
import com.cmoney.backend2.sample.model.WindowsLock
import com.cmoney.backend2.sample.view.main.data.LoginEvent
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainViewModel(
    private val manager: GlobalBackend2Manager,
    private val identityProviderWeb: IdentityProviderWeb
) : ViewModel() {

    private val _error =
        SingleLiveEvent<String>()
    val error: LiveData<String> = _error
    private val _windowsLock =
        SingleLiveEvent<WindowsLock>()
    val windowsLock: LiveData<WindowsLock> = _windowsLock
    private val _loginEvent =
        SingleLiveEvent<LoginEvent>()
    val loginEvent: LiveData<LoginEvent> = _loginEvent

    fun setAppId(id: Int) {
        manager.setAppId(id)
    }

    fun setClientId(id: String) {
        manager.setClientId(id)
    }

    fun setDomain(domain: String) {
        manager.setGlobalDomainUrl(domain)
    }

    fun login(account: String, password: String) {
        val md5edPassword = try {
            password.md5().orEmpty()
        } catch (e: Exception) {
            ""
        }
        if (account.isEmpty() || md5edPassword.isEmpty()) {
            return
        }
        viewModelScope.launch {
            _windowsLock.value = WindowsLock.Lock
            val result = identityProviderWeb.loginByEmail(account, md5edPassword)
            _windowsLock.value = WindowsLock.Unlock
            result.fold({ body ->
                manager.setAccessToken(
                    AccessToken(body.accessToken.orEmpty())
                )
                manager.setIdentityToken(
                    IdentityToken(body.idToken.orEmpty())
                )
                manager.setRefreshToken(
                    body.refreshToken.orEmpty()
                )
                _loginEvent.value = LoginEvent.Success
            }, {
                _error.value = it.message
            })
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun String.md5(): String? {
        val md5: MessageDigest = MessageDigest.getInstance("MD5")
        val digest: ByteArray = md5.digest(this.toByteArray(StandardCharsets.UTF_8))
        return java.lang.String.format("%032x", BigInteger(1, digest))
    }

}