package com.cmoney.backend2.base.model.manager

import com.cmoney.backend2.activity.model.ActivitySettingAdapter
import com.cmoney.backend2.activity.model.ActivitySettingAdapterImpl
import com.cmoney.backend2.authorization.model.AuthorizationSettingAdapter
import com.cmoney.backend2.authorization.model.AuthorizationSettingAdapterImpl
import com.cmoney.backend2.base.model.request.AccessToken
import com.cmoney.backend2.base.model.request.IdentityToken
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.backend.BackendSetting
import com.cmoney.backend2.base.model.setting.jwt.JwtSetting
import com.cmoney.backend2.billing.model.BillingSettingAdapter
import com.cmoney.backend2.billing.model.BillingSettingAdapterImpl
import com.cmoney.backend2.brokerdatatransmission.model.BrokerDataTransmissionSettingAdapter
import com.cmoney.backend2.brokerdatatransmission.model.BrokerDataTransmissionSettingAdapterImpl
import com.cmoney.backend2.cellphone.model.CellphoneSettingAdapter
import com.cmoney.backend2.cellphone.model.CellphoneSettingAdapterImpl
import com.cmoney.backend2.centralizedimage.model.CentralizedImageSettingAdapter
import com.cmoney.backend2.centralizedimage.model.CentralizedImageSettingAdapterImpl
import com.cmoney.backend2.chat.model.ChatRoomSettingAdapter
import com.cmoney.backend2.chat.model.ChatRoomSettingAdapterImpl
import com.cmoney.backend2.chipk.model.ChipKSettingAdapter
import com.cmoney.backend2.chipk.model.ChipKSettingAdapterImpl
import com.cmoney.backend2.clientconfiguration.model.ClientConfigurationSettingAdapter
import com.cmoney.backend2.clientconfiguration.model.ClientConfigurationSettingAdapterImpl
import com.cmoney.backend2.cmtalk.model.CMTalkSettingAdapter
import com.cmoney.backend2.cmtalk.model.CMTalkSettingAdapterImpl
import com.cmoney.backend2.common.model.CommonSettingAdapter
import com.cmoney.backend2.common.model.CommonSettingAdapterImpl
import com.cmoney.backend2.commonuse.model.CommonUseSettingAdapter
import com.cmoney.backend2.commonuse.model.CommonUseSettingAdapterImpl
import com.cmoney.backend2.crawlsetting.model.CrawlSettingSettingAdapter
import com.cmoney.backend2.crawlsetting.model.CrawlSettingSettingAdapterImpl
import com.cmoney.backend2.crm.model.CrmSettingAdapter
import com.cmoney.backend2.crm.model.CrmSettingAdapterImpl
import com.cmoney.backend2.customgroup.model.CustomGroupSettingAdapter
import com.cmoney.backend2.customgroup.model.CustomGroupSettingAdapterImpl
import com.cmoney.backend2.customgroup2.model.CustomGroup2SettingAdapter
import com.cmoney.backend2.customgroup2.model.CustomGroup2SettingAdapterImpl
import com.cmoney.backend2.data.model.DataSettingAdapter
import com.cmoney.backend2.data.model.DataSettingAdapterImpl
import com.cmoney.backend2.frontendlogger.model.FrontEndLoggerSettingAdapter
import com.cmoney.backend2.frontendlogger.model.FrontEndLoggerSettingAdapterImpl
import com.cmoney.backend2.virtualtrading2.model.settingadapter.VirtualTrading2SettingAdapter
import com.cmoney.backend2.virtualtrading2.model.settingadapter.VirtualTrading2SettingAdapterImpl
import com.cmoney.backend2.vtwebapi.model.settingadapter.VirtualTradeSettingAdapter
import com.cmoney.backend2.vtwebapi.model.settingadapter.VirtualTradeSettingAdapterImpl

/**
 * Backend模組經常變動設定的管理者。
 *
 * @property backendSetting Backend設定
 * @property jwtSetting Jwt設定
 * @property activitySettingAdapter 活動設定轉接器
 * @property authorizationSettingAdapter 授權設定轉接器
 * @property billingSettingAdapter Billing設定轉接器
 * @property brokerDataTransmissionSettingAdapter 券商庫存設定轉接器
 * @property cellphoneSettingAdapter 電話號碼設定轉接器
 * @property centralizedImageSettingAdapter 中央圖片設定轉接器
 * @property chatRoomSettingAdapter 聊天室設定轉接器
 * @property chipKSettingAdapter 籌碼K服務設定轉接器
 * @property clientConfigurationSettingAdapter 用戶端設定服務設定轉接器
 * @property cmTalkSettingAdapter CMTalk 服務設定轉接器
 * @property commonSettingAdapter MobileService通用設定轉接器
 * @property commonUseSettingAdapter CommonUse服務設定轉接器
 * @property crawlSettingSettingAdapter 爬蟲服務設定轉接器
 * @property crmSettingAdapter CRM設定轉接器
 * @property customGroupSettingAdapter MobileService-自選股設定轉接器
 * @property customGroup2SettingAdapter 自選股V2設定轉接器
 * @property dataSettingAdapter Data服務設定轉接器
 * @property frontEndLoggerSettingAdapter FrontEndLogger服務設定轉接器
 * @property virtualTradeSettingAdapter 虛擬下單V1轉接器
 * @property virtualTrading2SettingAdapter 虛擬下單V2轉接器
 *
 */
class GlobalBackend2Manager(
    private val backendSetting: BackendSetting,
    private val jwtSetting: JwtSetting,
    private val activitySettingAdapter: ActivitySettingAdapter,
    private val authorizationSettingAdapter: AuthorizationSettingAdapter,
    private val brokerDataTransmissionSettingAdapter: BrokerDataTransmissionSettingAdapter,
    private val billingSettingAdapter: BillingSettingAdapter,
    private val cellphoneSettingAdapter: CellphoneSettingAdapter,
    private val centralizedImageSettingAdapter: CentralizedImageSettingAdapter,
    private val chatRoomSettingAdapter: ChatRoomSettingAdapter,
    private val chipKSettingAdapter: ChipKSettingAdapter,
    private val clientConfigurationSettingAdapter: ClientConfigurationSettingAdapter,
    private val cmTalkSettingAdapter: CMTalkSettingAdapter,
    private val commonSettingAdapter: CommonSettingAdapter,
    private val commonUseSettingAdapter: CommonUseSettingAdapter,
    private val crawlSettingSettingAdapter: CrawlSettingSettingAdapter,
    private val crmSettingAdapter: CrmSettingAdapter,
    private val customGroupSettingAdapter: CustomGroupSettingAdapter,
    private val customGroup2SettingAdapter: CustomGroup2SettingAdapter,
    private val dataSettingAdapter: DataSettingAdapter,
    private val frontEndLoggerSettingAdapter: FrontEndLoggerSettingAdapter,
    private val virtualTradeSettingAdapter: VirtualTradeSettingAdapter,
    private val virtualTrading2SettingAdapter: VirtualTrading2SettingAdapter,
) {
    constructor(builder: Builder) : this(
        backendSetting = builder.backendSetting,
        jwtSetting = builder.jwtSetting,
        activitySettingAdapter = builder.activitySettingAdapter,
        authorizationSettingAdapter = builder.authorizationSettingAdapter,
        billingSettingAdapter = builder.billingSettingAdapter,
        brokerDataTransmissionSettingAdapter = builder.brokerDataTransmissionSettingAdapter,
        cellphoneSettingAdapter = builder.cellphoneSettingAdapter,
        centralizedImageSettingAdapter = builder.centralizedImageSettingAdapter,
        chatRoomSettingAdapter = builder.chatRoomSettingAdapter,
        chipKSettingAdapter = builder.chipKSettingAdapter,
        clientConfigurationSettingAdapter = builder.clientConfigurationSettingAdapter,
        cmTalkSettingAdapter = builder.cmTalkSettingAdapter,
        commonSettingAdapter = builder.commonSettingAdapter,
        commonUseSettingAdapter = builder.commonUseSettingAdapter,
        crawlSettingSettingAdapter = builder.crawlSettingSettingAdapter,
        crmSettingAdapter = builder.crmSettingAdapter,
        customGroupSettingAdapter = builder.customGroupSettingAdapter,
        customGroup2SettingAdapter = builder.customGroup2SettingAdapter,
        dataSettingAdapter = builder.dataSettingAdapter,
        frontEndLoggerSettingAdapter = builder.frontEndLoggerSettingAdapter,
        virtualTradeSettingAdapter = builder.virtualTradeSettingAdapter,
        virtualTrading2SettingAdapter = builder.virtualTrading2SettingAdapter
    )

    /**
     * 取得全域的DomainUrl
     */
    fun getGlobalDomainUrl(): String {
        return backendSetting.getDomainUrl()
    }

    /**
     * 設定全域的DomainUrl
     */
    fun setGlobalDomainUrl(domainUrl: String) {
        backendSetting.setDomainUrl(domainUrl)
    }

    /**
     * 取得App編號
     */
    fun getAppId(): Int {
        return backendSetting.getAppId()
    }

    /**
     * 設定App編號
     */
    fun setAppId(appId: Int) {
        backendSetting.setAppId(appId)
    }

    /**
     * 取得客戶端編號
     */
    fun getClientId(): String {
        return backendSetting.getClientId()
    }

    /**
     * 設定客戶端編號
     */
    fun setClientId(clientId: String) {
        backendSetting.setClientId(clientId)
    }

    /**
     * 取得App版本名稱
     */
    fun getAppVersionName(): String {
        return backendSetting.getAppVersionName()
    }

    /**
     * 取得App版本碼
     */
    fun getAppVersionCode(): Long {
        return backendSetting.getAppVersionCode()
    }

    /**
     * 取得製造商
     */
    fun getManufacturer(): String {
        return backendSetting.getManufacturer()
    }

    /**
     * 取得手機模型
     */
    fun getModel(): String {
        return backendSetting.getModel()
    }

    /**
     * 取得手機系統版本
     */
    fun getOsVersion(): String {
        return backendSetting.getOsVersion()
    }

    /**
     * 取得平台
     */
    fun getPlatform(): Platform {
        return backendSetting.getPlatform()
    }

    /**
     * 設定平台
     */
    fun setPlatform(platform: Platform) {
        backendSetting.setPlatform(platform)
    }

    /**
     * 取得Jwt的AccessToken
     */
    fun getAccessToken(): AccessToken {
        return jwtSetting.getAccessToken()
    }

    /**
     * 設定Jwt的AccessToken
     */
    fun setAccessToken(accessToken: AccessToken) {
        jwtSetting.setAccessToken(accessToken)
    }

    /**
     * 取得Jwt的IdentityToken
     */
    fun getIdentityToken(): IdentityToken {
        return jwtSetting.getIdentityToken()
    }

    /**
     * 設定Jwt的IdentityToken
     */
    fun setIdentityToken(identityToken: IdentityToken) {
        jwtSetting.setIdentityToken(identityToken)
    }

    /**
     * 取得Jwt的RefreshToken
     */
    fun getRefreshToken(): String {
        return jwtSetting.getRefreshToken()
    }

    /**
     * 設定Jwt的RefreshToken
     */
    fun setRefreshToken(refreshToken: String) {
        jwtSetting.setRefreshToken(refreshToken)
    }

    /**
     * 取得活動設定轉接器
     */
    fun getActivitySettingAdapter(): ActivitySettingAdapter {
        return activitySettingAdapter
    }

    /**
     * 取得授權設定轉接器
     */
    fun getAuthorizationSettingAdapter(): AuthorizationSettingAdapter {
        return authorizationSettingAdapter
    }

    /**
     * 取得Billing設定轉接器
     */
    fun getBillingSettingAdapter(): BillingSettingAdapter {
        return billingSettingAdapter
    }

    /**
     * 取得券商庫存設定轉接器
     */
    fun getBrokerDataTransmissionSettingAdapter(): BrokerDataTransmissionSettingAdapter {
        return brokerDataTransmissionSettingAdapter
    }

    /**
     * 取得電話號碼設定轉接器
     */
    fun getCellphoneSettingAdapter(): CellphoneSettingAdapter {
        return cellphoneSettingAdapter
    }

    /**
     * 取得中央圖片設定轉接器
     */
    fun getCentralizedImageSettingAdapter(): CentralizedImageSettingAdapter {
        return centralizedImageSettingAdapter
    }

    /**
     * 取得聊天室設定轉接器
     */
    fun getChatRoomSettingAdapter(): ChatRoomSettingAdapter {
        return chatRoomSettingAdapter
    }

    /**
     * 取得籌碼K服務設定轉接器
     *
     * @return 籌碼K服務設定轉接器
     */
    fun getChipKSettingAdapter(): ChipKSettingAdapter {
        return chipKSettingAdapter
    }

    /**
     * 取得用戶端設定服務設定轉接器
     *
     * @return 用戶端設定服務設定轉接器
     */
    fun getClientConfigurationSettingAdapter(): ClientConfigurationSettingAdapter {
        return clientConfigurationSettingAdapter
    }

    /**
     * 取得CMTalk 服務設定轉接器
     *
     * @return CMTalk 服務設定轉接器
     */
    fun getCMTalkSettingAdapter(): CMTalkSettingAdapter {
        return cmTalkSettingAdapter
    }

    /**
     * 取得MobileService通用設定轉接器
     *
     * @return MobileService通用設定轉接器
     */
    fun getCommonSettingAdapter(): CommonSettingAdapter {
        return commonSettingAdapter
    }

    /**
     * 取得CommonUse服務設定轉接器
     *
     * @return CommonUse服務設定轉接器
     */
    fun getCommonUseSettingAdapter(): CommonUseSettingAdapter {
        return commonUseSettingAdapter
    }

    /**
     * 取得爬蟲設定轉接器
     */
    fun getCrawlSettingSettingAdapter(): CrawlSettingSettingAdapter {
        return crawlSettingSettingAdapter
    }

    /**
     * 取得CRM設定轉接器
     */
    fun getCrmSettingAdapter(): CrmSettingAdapter {
        return crmSettingAdapter
    }

    /**
     * 取得MobileService-自選股設定轉接器
     *
     * @return MobileService-自選股設定轉接器
     */
    fun getCustomGroupSettingAdapter(): CustomGroupSettingAdapter {
        return customGroupSettingAdapter
    }

    /**
     * 取得自選股2設定轉接器
     */
    fun getCustomGroup2SettingAdapter(): CustomGroup2SettingAdapter {
        return customGroup2SettingAdapter
    }

    /**
     * 取得Data服務設定轉接器
     *
     * @return Data服務設定轉接器
     */
    fun getDataSettingAdapter(): DataSettingAdapter {
        return dataSettingAdapter
    }

    /**
     * 取得FrontEndLogger服務設定轉接器
     *
     * @return FrontEndLogger服務設定轉接器
     */
    fun getFrontEndLoggerSettingAdapter(): FrontEndLoggerSettingAdapter {
        return frontEndLoggerSettingAdapter
    }

    /**
     * 取得虛擬下單V1設定轉接器
     */
    fun getVirtualTradeSettingAdapter(): VirtualTradeSettingAdapter {
        return virtualTradeSettingAdapter
    }

    /**
     * 取得虛擬下單V2設定轉接器
     */
    fun getVirtualTrading2SettingAdapter(): VirtualTrading2SettingAdapter {
        return virtualTrading2SettingAdapter
    }

    /**
     * 清除Jwt設定，主要提供給登出使用。
     */
    fun clearJwtSetting() {
        jwtSetting.setAccessToken(AccessToken())
        jwtSetting.setIdentityToken(IdentityToken())
        jwtSetting.setRefreshToken("")
    }

    class Builder(
        val backendSetting: BackendSetting,
        val jwtSetting: JwtSetting,
    ) {
        constructor(builder: Builder) : this(
            backendSetting = builder.backendSetting,
            jwtSetting = builder.jwtSetting
        )

        var globalDomainUrl: String = backendSetting.getDomainUrl()
            set(value) {
                backendSetting.setDomainUrl(value)
                field = value
            }
        var appId: Int = backendSetting.getAppId()
            set(value) {
                backendSetting.setAppId(value)
                field = value
            }
        var clientId: String = backendSetting.getClientId()
            set(value) {
                backendSetting.setClientId(value)
                field = value
            }
        var platform: Platform = backendSetting.getPlatform()
            set(value) {
                backendSetting.setPlatform(value)
            }
        var activitySettingAdapter: ActivitySettingAdapter =
            ActivitySettingAdapterImpl(backendSetting)
        var authorizationSettingAdapter: AuthorizationSettingAdapter =
            AuthorizationSettingAdapterImpl(backendSetting)
        var billingSettingAdapter: BillingSettingAdapter =
            BillingSettingAdapterImpl(backendSetting)
        var brokerDataTransmissionSettingAdapter: BrokerDataTransmissionSettingAdapter =
            BrokerDataTransmissionSettingAdapterImpl(backendSetting)
        var cellphoneSettingAdapter: CellphoneSettingAdapter =
            CellphoneSettingAdapterImpl(backendSetting)
        var centralizedImageSettingAdapter: CentralizedImageSettingAdapter =
            CentralizedImageSettingAdapterImpl(backendSetting)
        var chatRoomSettingAdapter: ChatRoomSettingAdapter =
            ChatRoomSettingAdapterImpl()
        var chipKSettingAdapter: ChipKSettingAdapter =
            ChipKSettingAdapterImpl(backendSetting)
        var clientConfigurationSettingAdapter: ClientConfigurationSettingAdapter =
            ClientConfigurationSettingAdapterImpl(backendSetting)
        var cmTalkSettingAdapter: CMTalkSettingAdapter =
            CMTalkSettingAdapterImpl(backendSetting)
        var commonSettingAdapter: CommonSettingAdapter =
            CommonSettingAdapterImpl(backendSetting)
        var commonUseSettingAdapter: CommonUseSettingAdapter =
            CommonUseSettingAdapterImpl(backendSetting)
        var crawlSettingSettingAdapter: CrawlSettingSettingAdapter =
            CrawlSettingSettingAdapterImpl()
        var crmSettingAdapter: CrmSettingAdapter =
            CrmSettingAdapterImpl(backendSetting)
        var customGroupSettingAdapter: CustomGroupSettingAdapter =
            CustomGroupSettingAdapterImpl(backendSetting)
        var customGroup2SettingAdapter: CustomGroup2SettingAdapter =
            CustomGroup2SettingAdapterImpl(backendSetting)
        var dataSettingAdapter: DataSettingAdapter =
            DataSettingAdapterImpl()
        var frontEndLoggerSettingAdapter: FrontEndLoggerSettingAdapter =
            FrontEndLoggerSettingAdapterImpl(backendSetting)
        var virtualTradeSettingAdapter: VirtualTradeSettingAdapter =
            VirtualTradeSettingAdapterImpl(backendSetting)
        var virtualTrading2SettingAdapter: VirtualTrading2SettingAdapter =
            VirtualTrading2SettingAdapterImpl()

        fun build(): GlobalBackend2Manager = GlobalBackend2Manager(this)

        companion object {
            inline fun build(
                backendSetting: BackendSetting,
                jwtSetting: JwtSetting,
                block: Builder.() -> Unit
            ) = Builder(
                backendSetting = backendSetting,
                jwtSetting = jwtSetting
            ).apply(block)
                .build()
        }
    }
}