package com.cmoney.backend2.base.model.setting.backend

import com.cmoney.backend2.base.model.setting.Platform

/**
 * Backend2設定檔
 */
interface BackendSetting {

    fun getDomainUrl(): String

    fun setDomainUrl(domainUrl: String)

    fun getAppId(): Int

    fun setAppId(appId: Int)

    fun getClientId(): String

    fun setClientId(clientId: String)

    fun getAppVersionCode(): Long

    fun getAppVersionName(): String

    fun getPlatform(): Platform

    fun setPlatform(platform: Platform)

    fun getManufacturer(): String

    fun getModel(): String

    fun getOsVersion(): String
}