package com.cmoney.backend2.sample.servicecase.data

import android.util.Log
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken

data class AccountSettingInfo(
    val account :String,
    val password : String,
    val refreshToken : String,
    val identityToken : IdentityToken,
    val accessToken : String,
    val appId : Int
){
    companion object{
        fun AccountSettingInfo.changeUser(globalBackend2Manager: GlobalBackend2Manager){
            globalBackend2Manager.setRefreshToken(refreshToken)
            globalBackend2Manager.setIdentityToken(identityToken)
            globalBackend2Manager.setAccessToken(AccessToken(accessToken))
            globalBackend2Manager.setAppId(appId)
            Log.d("AccountSettingInfo","切換帳號$account")
        }
    }
}