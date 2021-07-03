package com.cmoney.backend2.cellphone.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.cellphone.service.api.bindcellphone.BindCellphoneResponseBodyWithError
import com.cmoney.backend2.cellphone.service.api.checkcellphonebindingverifycode.CheckCellphoneBindingVerifyCodeResponseBodyWithError
import com.cmoney.backend2.cellphone.service.api.checkverifycode.CellphoneCheckVerifyCodeWithError
import com.cmoney.backend2.cellphone.service.api.forgotpassword.CellphoneForgotPasswordWithError
import com.cmoney.backend2.cellphone.service.api.getaccountinfo.AccountInfoWithError
import com.cmoney.backend2.cellphone.service.api.getverifycode.CellphoneGetVerifyCodeWithError
import com.cmoney.backend2.cellphone.service.api.register.CellphoneRegisterWithError
import com.cmoney.backend2.cellphone.service.api.unbindcellphone.UnbindCellphoneResponseBodyWithError
import com.cmoney.backend2.cellphone.service.api.updatepassword.UpdatePasswordResponseBodyWithError
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * MobileService-手機註冊登入
 */
interface CellphoneService {

    /**
     * 服務1. 取得驗證碼 / 重寄驗證碼
     *
     * @param countryCode 註冊的手機國碼
     * @param cellphoneNumber 註冊的手機號碼(09開頭或9開頭都可以)
     *
     */
    @RecordApi
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun getVerifyCode(
        @Field("Action") action: String = "getregisterverifycode",
        @Field("CountryCode") countryCode: String,
        @Field("Cellphone") cellphoneNumber: String
    ): Response<CellphoneGetVerifyCodeWithError>

    /**
     * 服務2. 驗證註冊驗證碼
     *
     * @param countryCode 註冊的手機國碼
     * @param cellphoneNumber 註冊的手機號碼(09開頭或9開頭都可以)
     * @param verifyCode 驗證碼
     *
     */
    @RecordApi
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun checkVerifyCode(
        @Field("Action") action: String = "checkverifycode",
        @Field("CountryCode") countryCode: String,
        @Field("Cellphone") cellphoneNumber: String,
        @Field("VerifyCode") verifyCode: String
    ): Response<CellphoneCheckVerifyCodeWithError>

    /**
     * 服務3. 手機號碼註冊
     *
     * @param action String
     * @param countryCode 註冊的手機國碼 EX: 886
     * @param cellphoneNumber 註冊的手機號碼(09開頭或9開頭都可以)
     * @param password 註冊的密碼(MD5過)
     * @param platform 平台
     * @return Response<CellphoneRegisterWithError>
     */
    @RecordApi(isLogRequestBody = false)
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun registerByCellphone(
        @Header("x-cmapi-trace-context") xApiLog: String,
        @Field("Action") action: String = "phoneregistermember",
        @Field("CountryCode") countryCode: String,
        @Field("Cellphone") cellphoneNumber: String,
        @Field("Password") password: String,
        @Field("Device") platform: Int
    ): Response<CellphoneRegisterWithError>

    /**
     * 服務5. 忘記密碼 by手機
     *
     * @param countryCode 手機國碼
     * @param cellphoneNumber 手機號碼
     *
     */
    @RecordApi
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun forgotPasswordForCellphone(
        @Field("Action") action: String = "forgetpasswordbycellphone",
        @Field("CountryCode") countryCode: String,
        @Field("Cellphone") cellphoneNumber: String
    ): Response<CellphoneForgotPasswordWithError>

    /**
     * 服務6. 更改密碼
     *
     * @param guid 該會員Guid
     * @param appId App編號
     * @param oldPassword 舊密碼(MD5加密過)
     * @param newPassword 新密碼(MD5加密過)
     *
     */
    @RecordApi(isLogRequestBody = false)
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun updatePassword(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "updatepassword",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("OldPassword") oldPassword: String,
        @Field("NewPassword") newPassword: String
    ): Response<UpdatePasswordResponseBodyWithError>

    /**
     * 服務7. 取得帳號資訊
     *
     * @param guid 該會員的Guid
     * @param appId App編號
     *
     */
    @RecordApi
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun getAccountInfo(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getaccountinfo",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int
    ): Response<AccountInfoWithError>

    /**
     * 服務8. 手機綁定
     *
     * @param guid 該會員Guid
     * @param appId App編號
     * @param countryCode 手機國碼
     * @param cellphoneNumber 手機電話號碼
     *
     */
    @RecordApi
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun bindCellphone(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "bindcellphone",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("CountryCode") countryCode: String,
        @Field("Cellphone") cellphoneNumber: String
    ): Response<BindCellphoneResponseBodyWithError>


    /**
     * 服務9. 執行手機綁定驗證手機綁定驗證碼
     *
     * @param guid 該會員Guid
     * @param appId App編號
     * @param countryCode 手機國碼
     * @param cellphoneNumber 手機號碼(09開頭或9開頭都可以)
     * @param verifyCode 驗證碼(若已經使用服務2進行驗證可不填寫)
     */
    @RecordApi
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun checkCellphoneBindingVerifyCode(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "checkcellphonebindingverifycode",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("CountryCode") countryCode: String,
        @Field("Cellphone") cellphoneNumber: String,
        @Field("VerifyCode") verifyCode: String?
    ): Response<CheckCellphoneBindingVerifyCodeResponseBodyWithError>

    /**
     * 服務10. 取消手機綁定
     *
     * @param guid 該會員Guid
     * @param appId App編號
     *
     */
    @RecordApi
    @FormUrlEncoded
    @POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
    suspend fun unbindCellphone(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "unbindcellphone",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int
    ): Response<UnbindCellphoneResponseBodyWithError>
}