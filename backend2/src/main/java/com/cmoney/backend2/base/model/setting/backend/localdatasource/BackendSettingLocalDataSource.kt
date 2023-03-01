package com.cmoney.backend2.base.model.setting.backend.localdatasource

interface BackendSettingLocalDataSource {
    fun getDomainUrl(): String

    fun setDomainUrl(domainUrl: String)

    fun getAppId(): Int

    fun setAppId(appId: Int)

    fun getClientId(): String

    fun setClientId(clientId: String)

    fun getPlatform(): Int

    fun setPlatform(platform: Int)
}