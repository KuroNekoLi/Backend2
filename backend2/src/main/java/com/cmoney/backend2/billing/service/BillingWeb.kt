package com.cmoney.backend2.billing.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.billing.service.api.getproductinfo.ProductInformation
import com.cmoney.backend2.billing.service.common.*

interface BillingWeb {

    /**
     * Backend2管理者
     */
    val manager: GlobalBackend2Manager

    /**
     * 取得後台自定義的資料
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getDeveloperPayload(
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/CommonMethod/GetDeveloperPayLoadAsync"
    ): Result<Long>

    /**
     * 確認該平台是否開放應用程式內購買
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 如果是true，可以購買;false不可以購買。
     */
    suspend fun isReadyToPurchase(
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/CommonMethod/IsPurchasable/${manager.getPlatform().code}"
    ): Result<Boolean>

    /**
     * 取得可購買的商品列表
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 商品列表
     */
    suspend fun getProductInfo(
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/CommonMethod/ProductsInfo/${manager.getPlatform().code}"
    ): Result<List<ProductInformation>>

    /**
     * 取得App授權狀態
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 授權狀態
     */
    suspend fun getAuthStatus(
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<Authorization>

    /**
     * 取得其他App的授權狀態
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     *
     * @param queryAppId 要查詢的App
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 授權狀態
     */
    suspend fun getTargetAppAuthStatus(
        queryAppId: Int,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<Authorization>

    /**
     * 驗證華為InApp商品購買的訂單驗證
     *
     * @param receipt 收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun verifyHuaweiInAppReceipt(
        receipt: InAppHuaweiReceipt,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/HuaWei/VerifyHUAWEIOrderReceipt"
    ): Result<Unit>

    /**
     * 驗證華為Sub商品購買的訂單驗證
     *
     * @param receipt 收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun verifyHuaweiSubReceipt(
        receipt: SubHuaweiReceipt,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/HuaWei/VerifyHUAWEISubscriptReceipt"
    ): Result<Unit>

    /**
     * 恢復華為InApp商品權限
     *
     * @param receipts 所有收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun recoveryHuaweiInAppReceipt(
        receipts: List<InAppHuaweiReceipt>,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/HuaWei/RecoveryHUAWEIOrderReceipt"
    ): Result<Unit>

    /**
     * 恢復華為Sub商品權限
     *
     * @param receipts 所有收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun recoveryHuaweiSubReceipt(
        receipts: List<SubHuaweiReceipt>,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/HuaWei/RecoveryHUAWEISubscriptReceipt"
    ): Result<Unit>

    /**
     * 驗證Google的InApp商品購買的訂單驗證
     *
     * @param receipt 收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun verifyGoogleInAppReceipt(
        receipt: InAppGoogleReceipt,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/Google/VerifyOrderReceipt"
    ): Result<Unit>

    /**
     * 驗證Google的Sub商品購買的訂單驗證
     *
     * @param receipt 收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun verifyGoogleSubReceipt(
        receipt: SubGoogleReceipt,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/Google/VerifySubscriptReceipt"
    ): Result<Unit>

    /**
     * 恢復Google的InApp商品權限
     *
     * @param receipts 所有收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun recoveryGoogleInAppReceipt(
        receipts: List<InAppGoogleReceipt>,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/Google/RecoveryOrderReceipt"
    ): Result<Unit>

    /**
     * 恢復Google的Sub商品權限
     *
     * @param receipts 所有收據
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun recoveryGoogleSubReceipt(
        receipts: List<SubGoogleReceipt>,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/Google/RecoverySubscriptReceipt"
    ): Result<Unit>

    /**
     * 用戶是否有CMoney正在續約中的手機商品
     *
     * @param appId 指定的AppId
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAuthByCMoney(
        appId: Int,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/Order/AutorenewalingByCM/$appId",
    ): Result<Boolean>

    /**
     * 取得指定CMoney銷售類型的歷史訂閱數量
     *
     * @param productType 商品類型 EX:後台1.2商品都是 888003
     * @param functionIds 商品銷售代號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getHistoryCount(
        productType: Long,
        functionIds: Long,
        domain: String = manager.getBillingSettingAdapter().getDomain(),
        url: String = "${domain}PurchaseService/Statistics/Subscription/CMSales/HistoryCount"
    ):Result<Long>
}