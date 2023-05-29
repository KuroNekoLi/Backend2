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
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BillingService {
    /**
     * CommonMethod-取得後台自定義的資料
     */
    @RecordApi
    @POST
    suspend fun getDeveloperPayload(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: GetDeveloperPayloadRequestBody
    ): Response<GetDeveloperPayloadResponseBody>

    /**
     * CommonMethod-取得可購買的商品資訊
     */
    @RecordApi
    @POST
    suspend fun getIapProductInformation(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: GetProductInfoRequestBody
    ): Response<List<ProductInformation>>

    /**
     * CommonMethod-確認該平台是否開放應用程式內購買
     */
    @RecordApi
    @POST
    suspend fun isReadyToPurchase(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: IsPurchasableRequestBody
    ): Response<IsPurchasableResponseBody>

    /**
     * 華為-InApp商品購買的訂單驗證
     */
    @RecordApi
    @POST
    suspend fun verifyHuaweiInAppReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyHuaweiInAppReceiptRequestBody
    ): Response<Void>

    /**
     * 華為-Sub商品購買的訂單驗證
     */
    @RecordApi
    @POST
    suspend fun verifyHuaweiSubReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyHuaweiSubReceiptRequestBody
    ): Response<Void>

    /**
     * 華為-恢復InApp商品權限
     *
     */
    @RecordApi
    @POST
    suspend fun recoveryHuaweiInAppReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryHuaweiInAppReceiptRequestBody
    ): Response<Void>

    /**
     * 華為-恢復Sub商品權限
     *
     */
    @RecordApi
    @POST
    suspend fun recoveryHuaweiSubReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryHuaweiSubReceiptRequestBody
    ): Response<Void>

    /**
     * Google-InApp商品購買的訂單驗證
     */
    @RecordApi
    @POST
    suspend fun verifyGoogleInAppReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyGoogleInAppReceiptRequestBody
    ): Response<Void>

    /**
     * Google-Sub商品購買的訂單驗證
     */
    @RecordApi
    @POST
    suspend fun verifyGoogleSubReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: VerifyGoogleSubReceiptRequestBody
    ): Response<Void>

    /**
     * Google-恢復InApp商品權限
     */
    @RecordApi
    @POST
    suspend fun recoveryGoogleInAppReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryGoogleInAppReceiptRequestBody
    ): Response<Void>

    /**
     * Google-恢復Sub商品權限
     */
    @RecordApi
    @POST
    suspend fun recoveryGoogleSubReceipt(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RecoveryGoogleSubReceiptRequestBody
    ): Response<Void>

    /**
     * MobileService-取得是否有付費授權(加上身份識別)
     * 由於授權服務尚未完成，暫時放在這邊，之後會更動。
     */
    @RecordApi(cmoneyAction = "getauth")
    @FormUrlEncoded
    @POST
    suspend fun getAuthStatus(
        @Url url: String,
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
    @POST
    suspend fun getTargetAppAuthStatus(
        @Url url: String,
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
    @GET
    suspend fun getAuthByCMoney(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<AuthByCMoneyResponseBody>

    /**
     * 取得指定CMoney銷售類型的歷史訂閱數量
     * @param productType 商品類型 EX:後台1.2商品都是 888003
     * @param functionIds 商品銷售代號
     */
    @RecordApi
    @GET
    suspend fun getHistoryCount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("productType") productType: Long,
        @Query("functionIds") functionIds: Long
    ): Response<ResponseBody>

}