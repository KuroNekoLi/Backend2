package com.cmoney.backend2.base.model.setting.backend

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.backend.localdatasource.BackendSettingLocalDataSource

class BackendSettingImpl(
    context: Context,
    private val localDataSource: BackendSettingLocalDataSource
) : BackendSetting {

    private val applicationContext = context.applicationContext
    private var domainUrl: String = getDefaultDomainUrl()
    private var appId: Int = localDataSource.getAppId()
    private var clientId: String = localDataSource.getClientId()
    private val appVersionCode: Long = getAppVersionCode(applicationContext)
    private val appVersionName: String = getAppVersionName(applicationContext)
    private val manufacturer: String = Build.MANUFACTURER
    private val model: String = Build.MODEL
    private val osVersion: String = Build.VERSION.SDK_INT.toString()
    private var platform: Platform = getDefaultPlatform()

    private fun getDefaultPlatform(): Platform {
        val code = localDataSource.getPlatform()
        return Platform.valueOf(code) ?: Platform.Android
    }

    private fun getDefaultDomainUrl(): String {
        return localDataSource.getDomainUrl().takeIf {
            it.isNotEmpty()
        } ?: DEFAULT_DOMAIN_URL
    }

    private fun getAppVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "0.0.0"
        }
    }

    private fun getAppVersionCode(context: Context): Long {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            0
        }
    }

    override fun getDomainUrl(): String {
        return domainUrl
    }

    override fun setDomainUrl(domainUrl: String) {
        this.domainUrl = domainUrl
        localDataSource.setDomainUrl(domainUrl)
    }

    override fun getAppId(): Int {
        return appId
    }

    override fun setAppId(appId: Int) {
        this.appId = appId
        localDataSource.setAppId(appId)
    }

    override fun getClientId(): String {
        return clientId
    }

    override fun setClientId(clientId: String) {
        this.clientId = clientId
        localDataSource.setClientId(clientId)
    }

    override fun getAppVersionCode(): Long {
        return appVersionCode
    }

    override fun getAppVersionName(): String {
        return appVersionName
    }

    override fun getPlatform(): Platform {
        return platform
    }

    override fun setPlatform(platform: Platform) {
        this.platform = platform
        localDataSource.setPlatform(platform.code)
    }

    override fun getManufacturer(): String {
        return manufacturer
    }

    override fun getModel(): String {
        return model
    }

    override fun getOsVersion(): String {
        return osVersion
    }

    companion object {
        private const val DEFAULT_DOMAIN_URL = "https://www.cmoney.tw/"
    }
}