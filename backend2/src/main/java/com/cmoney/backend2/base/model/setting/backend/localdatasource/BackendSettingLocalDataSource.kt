package com.cmoney.backend2.base.model.setting.backend.localdatasource

interface BackendSettingLocalDataSource {
    /**
     * 取得Domain
     */
    fun getDomainUrl(): String

    /**
     * 設定Domain
     */
    fun setDomainUrl(domainUrl: String)

    /**
     * 取得CMoney定義的AppId
     */
    fun getAppId(): Int

    /**
     * 設定CMoney定義的AppId
     */
    fun setAppId(appId: Int)

    /**
     * 取得CMoney定義的ClientId
     */
    fun getClientId(): String

    /**
     * 設定CMoney定義的ClientId
     */
    fun setClientId(clientId: String)

    /**
     * 取得CMoney定義的平台
     */
    fun getPlatform(): Int

    /**
     * 設定CMoney定義的平台
     */
    fun setPlatform(platform: Int)
}