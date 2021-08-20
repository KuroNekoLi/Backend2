package com.cmoney.backend2.billing.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.billing.service.api.authbycmoney.AuthByCMoneyResponseBody
import com.cmoney.backend2.billing.service.api.getappauth.GetAppAuthResponseBody
import com.cmoney.backend2.billing.service.api.getauth.GetAuthResponseBody
import com.cmoney.backend2.billing.service.api.getdevelpoerpayload.GetDeveloperPayloadRequestBody
import com.cmoney.backend2.billing.service.api.getdevelpoerpayload.GetDeveloperPayloadResponseBody
import com.cmoney.backend2.billing.service.api.getproductinfo.GetProductInfoRequestBody
import com.cmoney.backend2.billing.service.api.getproductinfo.ProductInformation
import com.cmoney.backend2.billing.service.api.isPurchasable.IsPurchasableRequestBody
import com.cmoney.backend2.billing.service.api.isPurchasable.IsPurchasableResponseBody
import com.cmoney.backend2.billing.service.api.recoverygoogleinappreceipt.RecoveryGoogleInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.recoverygooglesubreceipt.RecoveryGoogleSubReceiptRequestBody
import com.cmoney.backend2.billing.service.api.recoveryhuaweiinappreceipt.RecoveryHuaweiInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.recoveryhuaweisubreceipt.RecoveryHuaweiSubReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifygoogleinappreceipt.VerifyGoogleInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifygooglesubreceipt.VerifyGoogleSubReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifyhuaweiinappreceipt.VerifyHuaweiInAppReceiptRequestBody
import com.cmoney.backend2.billing.service.api.verifyhuaweisubreceipt.VerifyHuaweiSubReceiptRequestBody
import retrofit2.Response
import retrofit2.http.*

interface BillingService {
    /**
     * CommonMethod-取得後台自定義的資料
     */
    @RecordApi
    @POST("PurchaseService/CommonMethod/GetDeveloperPayLoadAsync")
    suspend fun getDeveloperPayload(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetDeveloperPayloadRequestBody
    ): Response<GetDeveloperPayloadResponseBody>

    /**
     * CommonMethod-取得可購買的商品資訊
     */
    @RecordApi
    @POST("PurchaseService/CommonMethod/ProductsInfo/{device}")
    suspend fun getIapProductInformation(
        @Path("device") platform: Int,
        @Header("Authorization") authorization: String,
        @Body requestBody: GetProductInfoRequestBody
    ): Response<List<ProductInformation>>

    /**
     * CommonMethod-確認該平台是否開放應用程式內購買
     */
    @RecordApi
    @POST("PurchaseService/CommonMethod/IsPurchasable/{device}")
    suspend fun isReadyToPurchase(
        @Path("device") platform: Int,
        @Header("Authorization") authorization: String,
        @Body requestBody: IsPurchasableRequestBody
    ): Response<IsPurchasableResponseBody>

    /**
     * 華為-InApp商品購買的訂單驗證
     */
    @RecordApi
    @POST("PurchaseService/HuaWei/VerifyHUAWEIOrderReceipt")
    suspend fun verifyHuaweiInAppReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyHuaweiInAppReceiptRequestBody
    ): Response<Void>

    /**
     * 華為-Sub商品購買的訂單驗證
     */
    @RecordApi
    @POST("PurchaseService/HuaWei/VerifyHUAWEISubscriptReceipt")
    suspend fun verifyHuaweiSubReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyHuaweiSubReceiptRequestBody
    ): Response<Void>

    /**
     * 華為-恢復InApp商品權限
     *
     */
    @RecordApi
    @POST("PurchaseService/HuaWei/RecoveryHUAWEIOrderReceipt")
    suspend fun recoveryHuaweiInAppReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryHuaweiInAppReceiptRequestBody
    ): Response<Void>

    /**
     * 華為-恢復Sub商品權限
     *
     */
    @RecordApi
    @POST("PurchaseService/HuaWei/RecoveryHUAWEISubscriptReceipt")
    suspend fun recoveryHuaweiSubReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryHuaweiSubReceiptRequestBody
    ): Response<Void>

    /**
     * Google-InApp商品購買的訂單驗證
     */
    @RecordApi
    @POST("PurchaseService/Google/VerifyOrderReceipt")
    suspend fun verifyGoogleInAppReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyGoogleInAppReceiptRequestBody
    ): Response<Void>

    /**
     * Google-Sub商品購買的訂單驗證
     */
    @RecordApi
    @POST("PurchaseService/Google/VerifySubscriptReceipt")
    suspend fun verifyGoogleSubReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyGoogleSubReceiptRequestBody
    ): Response<Void>

    /**
     * Google-恢復InApp商品權限
     */
    @RecordApi
    @POST("PurchaseService/Google/RecoveryOrderReceipt")
    suspend fun recoveryGoogleInAppReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryGoogleInAppReceiptRequestBody
    ): Response<Void>

    /**
     * Google-恢復Sub商品權限
     */
    @RecordApi
    @POST("PurchaseService/Google/RecoverySubscriptReceipt")
    suspend fun recoveryGoogleSubReceipt(
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryGoogleSubReceiptRequestBody
    ): Response<Void>

    /**
     * MobileService-取得是否有付費授權(加上身份識別)
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     */
    @RecordApi(cmoneyAction = "getauth")
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun getAuthStatus(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getauth",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int
    ): Response<GetAuthResponseBody>

    /**
     * MobileService-取得指定App是否有付費授權
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     */
    @RecordApi(cmoneyAction = "getappauth")
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun getTargetAppAuthStatus(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getappauth",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("QueryAppId") queryAppId: Int
    ): Response<GetAppAuthResponseBody>

    /**
     * 用戶是否有CMoney正在續約中的手機商品
     */
    @RecordApi
    @GET("PurchaseService/Order/AutorenewalingByCM/{appId}")
    suspend fun getAuthByCMoney(
        @Header("Authorization") authorization: String,
        @Path("appId") appId: Int
    ): Response<AuthByCMoneyResponseBody>

}