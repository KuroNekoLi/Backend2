package com.cmoney.backend2.billing.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.billing.service.api.getproductinfo.ProductInformation
import com.cmoney.backend2.billing.service.common.*

interface BillingWeb {
    /**
     * 取得後台自定義的資料
     *
     * @return 自定義的標記
     */
    suspend fun getDeveloperPayload(): Result<Long>

    /**
     * 確認該平台是否開放應用程式內購買
     *
     * @return 如果是true，可以購買;false不可以購買。
     */
    suspend fun isReadyToPurchase(): Result<Boolean>

    /**
     * 取得可購買的商品列表
     *
     * @return 商品列表
     */
    suspend fun getProductInfo(): Result<List<ProductInformation>>

    /**
     * 取得App授權狀態
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     *
     * @param memberApiParam 會員相關參數
     *
     * @return 授權狀態
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getAuthStatus(memberApiParam: MemberApiParam): Result<Authorization>

    /**
     * 取得App授權狀態
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     *
     * @return 授權狀態
     */
    suspend fun getAuthStatus(): Result<Authorization>

    /**
     * 取得其他App的授權狀態
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     *
     * @param queryAppId 要查詢的App
     *
     * @return 授權狀態
     */
    suspend fun getTargetAppAuthStatus(queryAppId: Int): Result<Authorization>

    /**
     * 驗證華為InApp商品購買的訂單驗證
     *
     * @param receipt 收據
     *
     */
    suspend fun verifyHuaweiInAppReceipt(receipt: InAppHuaweiReceipt): Result<Unit>

    /**
     * 驗證華為Sub商品購買的訂單驗證
     *
     * @param receipt 收據
     *
     */
    suspend fun verifyHuaweiSubReceipt(receipt: SubHuaweiReceipt): Result<Unit>

    /**
     * 恢復華為InApp商品權限
     *
     * @param receipts 所有收據
     *
     */
    suspend fun recoveryHuaweiInAppReceipt(receipts: List<InAppHuaweiReceipt>): Result<Unit>

    /**
     * 恢復華為Sub商品權限
     *
     * @param receipts 所有收據
     *
     */
    suspend fun recoveryHuaweiSubReceipt(receipts: List<SubHuaweiReceipt>): Result<Unit>

    /**
     * 驗證Google的InApp商品購買的訂單驗證
     *
     * @param receipt 收據
     *
     */
    suspend fun verifyGoogleInAppReceipt(receipt: InAppGoogleReceipt): Result<Unit>

    /**
     * 驗證Google的Sub商品購買的訂單驗證
     *
     * @param receipt 收據
     *
     */
    suspend fun verifyGoogleSubReceipt(receipt: SubGoogleReceipt): Result<Unit>

    /**
     * 恢復Google的InApp商品權限
     *
     * @param receipts 所有收據
     *
     */
    suspend fun recoveryGoogleInAppReceipt(receipts: List<InAppGoogleReceipt>): Result<Unit>

    /**
     * 恢復Google的Sub商品權限
     *
     * @param receipts 所有收據
     *
     */
    suspend fun recoveryGoogleSubReceipt(receipts: List<SubGoogleReceipt>): Result<Unit>

    /**
     * 用戶是否有CMoney正在續約中的手機商品
     */
    suspend fun getAuthByCMoney(appId: Int): Result<Boolean>
}