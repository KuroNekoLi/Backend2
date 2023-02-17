package com.cmoney.backend2.base.model.manager

import android.content.Context
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.virtualtrading2.model.settingadapter.VirtualTrading2SettingAdapter
import com.cmoney.backend2.virtualtrading2.model.settingadapter.VirtualTrading2SettingAdapterImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GlobalBackend2Manager(
    private val context: Context,
    private val virtualTrading2SettingAdapter: VirtualTrading2SettingAdapter
) : KoinComponent {
    constructor(builder: Builder) : this(
        context = builder.context,
        virtualTrading2SettingAdapter = builder.virtualTrading2SettingAdapter
    )

    private val setting = get<Setting>(BACKEND2_SETTING)

//    private fun getAppVersionName(context: Context): String {
//        return try {
//            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
//            packageInfo.versionName
//        } catch (e: PackageManager.NameNotFoundException) {
//            "0.0.0"
//        }
//    }
//
//    private fun getAppVersionCode(context: Context): Long {
//        return try {
//            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
//            packageInfo.longVersionCode
//        } catch (e: PackageManager.NameNotFoundException) {
//            0
//        }
//    }

    /**
     * 取得全域的DomainUrl
     */
    fun getGlobalDomainUrl(): String {
        return setting.domainUrl
    }

    /**
     * 取得App編號
     */
    fun getAppId(): Int {
        return setting.appId
    }

    /**
     * 取得客戶端編號
     */
    fun getClientId(): String {
        return setting.clientId
    }

    /**
     * 取得App版本名稱
     */
    fun getAppVersionName(): String {
        return setting.appVersion
    }

    /**
     * 取得App版本碼
     */
    fun getAppVersionCode(): Long {
        return setting.appVersionCode.toLong()
    }

    /**
     * 取得製造商
     */
    fun getManufacturer(): String {
        return android.os.Build.MANUFACTURER
    }

    /**
     * 取得手機模型
     */
    fun getModel(): String {
        return android.os.Build.MODEL
    }

    /**
     * 取得手機系統版本
     */
    fun getOsVersion(): String {
        return android.os.Build.VERSION.SDK_INT.toString()
    }

    /**
     * 取得平台
     */
    fun getPlatform(): Platform {
        return setting.platform
    }

    /**
     * 取得授權連接Token
     */
    fun getAccessToken(): AccessToken {
        return setting.accessToken
    }

    /**
     * 取得會員資訊辨識Token
     */
    fun getIdentityToken(): IdentityToken {
        return setting.identityToken
    }

    /**
     * 取得刷新Token
     */
    fun getRefreshToken(): String {
        return setting.refreshToken
    }

    /**
     * 取得虛擬下單V2設定轉接器
     */
    fun getVirtualTrading2SettingAdapter(): VirtualTrading2SettingAdapter {
        return virtualTrading2SettingAdapter
    }

    class Builder(
        val context: Context
    ) {
        constructor(builder: Builder) : this(
            context = builder.context
        )

        var virtualTrading2SettingAdapter: VirtualTrading2SettingAdapter =
            VirtualTrading2SettingAdapterImpl()

        fun build() = GlobalBackend2Manager(this)

        companion object {
            inline fun build(
                context: Context,
                block: Builder.() -> Unit
            ) = Builder(
                context = context
            ).apply(block)
                .build()
        }
    }
}