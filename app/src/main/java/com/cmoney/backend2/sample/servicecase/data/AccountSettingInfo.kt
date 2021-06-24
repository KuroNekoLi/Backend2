package com.cmoney.backend2.sample.servicecase.data

import android.util.Log
import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.base.model.setting.Setting

data class AccountSettingInfo(
    val account :String,
    val password : String,
    val refreshToken : String,
    val identityToken : IdentityToken,
    val accessToken : String,
    val appId : Int
){
    companion object{
        fun AccountSettingInfo.changeUser(setting: Setting){
            setting.refreshToken = refreshToken
            setting.identityToken = identityToken
            setting.accessToken = AccessToken(accessToken)
            setting.appId = appId
            Log.d("AccountSettingInfo","切換帳號$account")
        }
    }
}