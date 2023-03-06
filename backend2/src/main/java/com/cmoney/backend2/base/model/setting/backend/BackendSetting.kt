package com.cmoney.backend2.base.model.setting.backend

import com.cmoney.backend2.base.model.setting.Platform

/**
 * Backend2設定檔
 */
interface BackendSetting {

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
     * 取得App版本代號
     */
    fun getAppVersionCode(): Long

    /**
     * 設定App版本名稱
     */
    fun getAppVersionName(): String

    /**
     * 取得CMoney定義的平台
     */
    fun getPlatform(): Platform

    /**
     * 設定CMoney定義的平台
     */
    fun setPlatform(platform: Platform)

    /**
     * 取得手機的製造廠商
     */
    fun getManufacturer(): String

    /**
     * 取得手機的Model名稱
     */
    fun getModel(): String

    /**
     * 取得手機系統版本
     */
    fun getOsVersion(): String
}