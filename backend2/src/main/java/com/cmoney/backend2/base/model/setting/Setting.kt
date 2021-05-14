package com.cmoney.backend2.base.model.setting

import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken

/**
 *
 * Backend設定檔
 *
 * @property domainUrl API網址
 * @property appId App的Id
 * @property clientId 客戶端的String id
 * @property appVersionCode App的版本，給工程師看
 * @property appVersion App的版本，給使用者看
 * @property manufacturer 製造商
 * @property model 手機型號
 * @property osVersion 系統版本
 * @property platform 平台
 * @property accessToken JWT的[AccessToken]
 * @property identityToken JWT的[IdentityToken]
 * @property refreshToken JWT的RefreshToken
 */
interface Setting {
    var domainUrl: String
    var appId: Int
    var clientId: String
    var appVersionCode: Int
    var appVersion: String
    val manufacturer: String
    val model: String
    val osVersion: String
    var platform: Platform
    var accessToken: AccessToken
    var identityToken: IdentityToken
    var refreshToken: String

    /**
     * 清除設定
     */
    fun clear()
}