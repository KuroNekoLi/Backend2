package com.cmoney.backend2.profile.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.profile.service.api.changepassword.ChangePasswordRequestBody
import com.cmoney.backend2.profile.service.api.checkemailcode.CheckEmailCodeRequestBody
import com.cmoney.backend2.profile.service.api.checkphonecode.CheckSmsCodeRequestBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail.GetRegistrationCodeByEmailRequestBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail.GetRegistrationCodeByEmailResponseBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone.GetRegistrationCodeByPhoneRequestBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone.GetRegistrationCodeByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.convertguestbyemail.ConvertGuestByEmailRequestBody
import com.cmoney.backend2.profile.service.api.convertguestbyphone.ConvertGuestBySmsRequestBody
import com.cmoney.backend2.profile.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.profile.service.api.getmyusergraphqlinfo.GetMyUserGraphQLInfoRequestBody
import com.cmoney.backend2.profile.service.api.getusergraphqlinfo.GetUserGraphQLInfoRequestBody
import com.cmoney.backend2.profile.service.api.linkcontactemail.LinkContactEmailRequestBody
import com.cmoney.backend2.profile.service.api.linkemail.LinkEmailRequestBody
import com.cmoney.backend2.profile.service.api.linkfacebook.LinkFacebookRequestBody
import com.cmoney.backend2.profile.service.api.linkphone.LinkPhoneRequestBody
import com.cmoney.backend2.profile.service.api.resetpassword.ResetPasswordBySmsRequestBody
import com.cmoney.backend2.profile.service.api.resetpasswordemail.ResetPasswordByEmailRequestBody
import com.cmoney.backend2.profile.service.api.sendforgotpasswordemail.SendForgotPasswordEmailRequestBody
import com.cmoney.backend2.profile.service.api.sendverificationemail.SendVerificationEmailRequestBody
import com.cmoney.backend2.profile.service.api.sendverificationsms.SendVerificationSmsRequestBody
import com.cmoney.backend2.profile.service.api.signupbyemail.SignUpByEmailRequestBody
import com.cmoney.backend2.profile.service.api.signupcompletebyemail.SignUpCompleteByEmailRequestBody
import com.cmoney.backend2.profile.service.api.signupcompletebyemail.SignUpCompleteByEmailResponseBody
import com.cmoney.backend2.profile.service.api.signupcompletebyphone.SignUpCompleteByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.signupcompletebyphone.SignupCompleteByPhoneRequestBody
import com.cmoney.backend2.profile.service.api.singupbyphone.SignUpByPhoneRequestBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ProfileService {
    /**
     * 取得帳戶綁定資訊
     */
    @RecordApi
    @GET
    suspend fun getAccount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GetAccountResponseBody>

    /**
     * 送出驗證信
     */
    @RecordApi
    @POST
    suspend fun sendVerificationEmail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: SendVerificationEmailRequestBody
    ): Response<Void>

    /**
     * 發送忘記密碼驗證信
     */
    @RecordApi
    @POST
    suspend fun sendForgotPasswordEmail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: SendForgotPasswordEmailRequestBody
    ): Response<Void>

    /**
     * 送出驗證簡訊
     */
    @RecordApi
    @POST
    suspend fun sendVerificationSms(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: SendVerificationSmsRequestBody
    ): Response<Void>

    /**
     * 確認信箱驗證碼是否有效
     */
    @RecordApi
    @POST
    suspend fun checkCodeEmail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CheckEmailCodeRequestBody
    ): Response<Void>

    /**
     * 確認簡訊驗證碼是否有效
     */
    @RecordApi
    @POST
    suspend fun checkCodeSms(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CheckSmsCodeRequestBody
    ): Response<Void>

    /**
     * 信箱綁定
     */
    @RecordApi
    @POST
    suspend fun linkEmail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: LinkEmailRequestBody
    ): Response<Void>

    /**
     * 手機綁定
     */
    @RecordApi
    @POST
    suspend fun linkPhone(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: LinkPhoneRequestBody
    ): Response<Void>

    /**
     * Facebook綁定
     */
    @RecordApi
    @POST
    suspend fun linkFacebook(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: LinkFacebookRequestBody
    ): Response<Void>

    /**
     * 聯絡信箱綁定
     */
    @RecordApi
    @POST
    suspend fun linkContactEmail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: LinkContactEmailRequestBody
    ): Response<Void>

    /**
     * 訪客帳號轉正綁定手機，此方法會更新訪客的密碼並將訪客綁定刪除
     */
    @RecordApi
    @POST
    suspend fun convertGuestBySms(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: ConvertGuestBySmsRequestBody
    ): Response<Void>

    /**
     * 訪客帳號轉正綁定信箱，此方法會更新訪客的密碼並將訪客綁定刪除
     */
    @RecordApi
    @POST
    suspend fun convertGuestByEmail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: ConvertGuestByEmailRequestBody
    ): Response<Void>

    /**
     * 更改密碼
     */
    @RecordApi
    @POST
    suspend fun changePassword(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: ChangePasswordRequestBody
    ): Response<Void>

    /**
     * 信箱重置密碼
     */
    @RecordApi
    @POST
    suspend fun resetPasswordByEmail(
        @Url url: String,
        @Body body: ResetPasswordByEmailRequestBody
    ): Response<Void>

    /**
     * 用簡訊重置密碼
     */
    @RecordApi
    @POST
    suspend fun resetPasswordBySms(
        @Url url: String,
        @Body body: ResetPasswordBySmsRequestBody
    ): Response<Void>

    /**
     * 信箱註冊
     */
    @RecordApi
    @POST
    suspend fun signUpByEmail(
        @Url url: String,
        @Body body: SignUpByEmailRequestBody
    ): Response<Void>

    /**
     * 手機註冊
     */
    @RecordApi
    @POST
    suspend fun signUpByPhone(
        @Url url: String,
        @Body body: SignUpByPhoneRequestBody
    ): Response<Void>

    /**
     * 完成Email註冊程序
     */
    @RecordApi
    @POST
    suspend fun signUpCompleteByEmail(
        @Url url: String,
        @Body body: SignUpCompleteByEmailRequestBody
    ): Response<SignUpCompleteByEmailResponseBody>

    /**
     * 完成手機註冊程序
     */
    @RecordApi
    @POST
    suspend fun signUpCompleteByPhone(
        @Url url: String,
        @Body body: SignupCompleteByPhoneRequestBody
    ): Response<SignUpCompleteByPhoneResponseBody>

    /**
     * 用信箱取得註冊碼
     */
    @RecordApi
    @POST
    suspend fun getRegistrationCodeByEmail(
        @Url url: String,
        @Body body: GetRegistrationCodeByEmailRequestBody
    ): Response<GetRegistrationCodeByEmailResponseBody>

    /**
     * 用簡訊取得註冊碼
     */
    @RecordApi
    @POST
    suspend fun getRegistrationCodeByPhone(
        @Url url: String,
        @Body body: GetRegistrationCodeByPhoneRequestBody
    ): Response<GetRegistrationCodeByPhoneResponseBody>

    /**
     * 取得自己的使用者資訊
     * */
    @RecordApi
    @POST
    suspend fun getMyUserGraphQlInfo(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetMyUserGraphQLInfoRequestBody
    ): Response<ResponseBody>

    /**
     * 更新自己的使用者資訊
     */
    @RecordApi
    @POST
    suspend fun mutationMyUserGraphQlInfo(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    /**
     *  依照使用者ID 取得暱稱及頭項資訊
     *
     *  @return 傳出ResponseBody，由外面在做解析
     */
    @RecordApi
    @POST
    suspend fun getUserGraphQLInfo(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetUserGraphQLInfoRequestBody
    ): Response<ResponseBody>

}
