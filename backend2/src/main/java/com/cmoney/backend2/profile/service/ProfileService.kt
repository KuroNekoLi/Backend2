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

interface ProfileService {
    /**
     * 取得帳戶綁定資訊
     */
    @RecordApi
    @GET("profile/account")
    suspend fun getAccount(
        @Header("Authorization") authorization: String
    ): Response<GetAccountResponseBody>

    /**
     * 送出驗證信
     */
    @RecordApi
    @POST("profile/verification/email")
    suspend fun sendVerificationEmail(
        @Header("Authorization") authorization: String,
        @Body body: SendVerificationEmailRequestBody
    ): Response<Void>

    /**
     * 發送忘記密碼驗證信
     */
    @RecordApi
    @POST("profile/verification/forgotPassword/email")
    suspend fun sendForgotPasswordEmail(
        @Header("Authorization") authorization: String,
        @Body body: SendForgotPasswordEmailRequestBody
    ): Response<Void>

    /**
     * 送出驗證簡訊
     */
    @RecordApi
    @POST("profile/verification/sms")
    suspend fun sendVerificationSms(
        @Header("Authorization") authorization: String,
        @Body body: SendVerificationSmsRequestBody
    ): Response<Void>

    /**
     * 確認信箱驗證碼是否有效
     */
    @RecordApi
    @POST("profile/verification/code/Check/email")
    suspend fun checkCodeEmail(
        @Header("Authorization") authorization: String,
        @Body body: CheckEmailCodeRequestBody
    ): Response<Void>

    /**
     * 確認簡訊驗證碼是否有效
     */
    @RecordApi
    @POST("profile/verification/code/Check/sms")
    suspend fun checkCodeSms(
        @Header("Authorization") authorization: String,
        @Body body: CheckSmsCodeRequestBody
    ): Response<Void>

    /**
     * 信箱綁定
     */
    @RecordApi
    @POST("profile/account/link/email")
    suspend fun linkEmail(
        @Header("Authorization") authorization: String,
        @Body body: LinkEmailRequestBody
    ): Response<Void>

    /**
     * 手機綁定
     */
    @RecordApi
    @POST("profile/account/link/cellphone")
    suspend fun linkPhone(
        @Header("Authorization") authorization: String,
        @Body body: LinkPhoneRequestBody
    ): Response<Void>

    /**
     * Facebook綁定
     */
    @RecordApi
    @POST("profile/account/link/facebook")
    suspend fun linkFacebook(
        @Header("Authorization") authorization: String,
        @Body body: LinkFacebookRequestBody
    ): Response<Void>

    /**
     * 聯絡信箱綁定
     */
    @RecordApi
    @POST("profile/account/link/contactEmail")
    suspend fun linkContactEmail(
        @Header("Authorization") authorization: String,
        @Body body: LinkContactEmailRequestBody
    ): Response<Void>

    /**
     * 訪客帳號轉正綁定手機，此方法會更新訪客的密碼並將訪客綁定刪除
     */
    @RecordApi
    @POST("profile/account/convertGuest/cellphone")
    suspend fun convertGuestBySms(
        @Header("Authorization") authorization: String,
        @Body body: ConvertGuestBySmsRequestBody
    ): Response<Void>

    /**
     * 訪客帳號轉正綁定信箱，此方法會更新訪客的密碼並將訪客綁定刪除
     */
    @RecordApi
    @POST("profile/account/convertGuest/email")
    suspend fun convertGuestByEmail(
        @Header("Authorization") authorization: String,
        @Body body: ConvertGuestByEmailRequestBody
    ): Response<Void>

    /**
     * 更改密碼
     */
    @RecordApi
    @POST("profile/account/password/change")
    suspend fun changePassword(
        @Header("Authorization") authorization: String,
        @Body body: ChangePasswordRequestBody
    ): Response<Void>

    /**
     * 信箱重置密碼
     */
    @RecordApi
    @POST("profile/account/password/reset/email")
    suspend fun resetPasswordByEmail(
        @Body body: ResetPasswordByEmailRequestBody
    ): Response<Void>

    /**
     * 用簡訊重置密碼
     */
    @RecordApi
    @POST("profile/account/password/reset/sms")
    suspend fun resetPasswordBySms(
        @Body body: ResetPasswordBySmsRequestBody
    ): Response<Void>

    /**
     * 信箱註冊
     */
    @RecordApi
    @POST("profile/signup/email")
    suspend fun signUpByEmail(
        @Body body: SignUpByEmailRequestBody
    ): Response<Void>

    /**
     * 手機註冊
     */
    @RecordApi
    @POST("profile/signup/cellphone")
    suspend fun signUpByPhone(
        @Body body: SignUpByPhoneRequestBody
    ): Response<Void>

    /**
     * 完成Email註冊程序
     */
    @RecordApi
    @POST("profile/signup/complete/email")
    suspend fun signUpCompleteByEmail(
        @Body body: SignUpCompleteByEmailRequestBody
    ): Response<SignUpCompleteByEmailResponseBody>

    /**
     * 完成手機註冊程序
     */
    @RecordApi
    @POST("profile/signup/complete/cellphone")
    suspend fun signUpCompleteByPhone(
        @Body body: SignupCompleteByPhoneRequestBody
    ): Response<SignUpCompleteByPhoneResponseBody>

    /**
     * 用信箱取得註冊碼
     */
    @RecordApi
    @POST("profile/signup/registrationcode/Get/email")
    suspend fun getRegistrationCodeByEmail(
        @Body body: GetRegistrationCodeByEmailRequestBody
    ): Response<GetRegistrationCodeByEmailResponseBody>

    /**
     * 用簡訊取得註冊碼
     */
    @RecordApi
    @POST("profile/signup/registrationcode/Get/sms")
    suspend fun getRegistrationCodeByPhone(
        @Body body: GetRegistrationCodeByPhoneRequestBody
    ): Response<GetRegistrationCodeByPhoneResponseBody>

    /**
     * 取得自己的使用者資訊
     * */
    @RecordApi
    @POST("profile/graphql/query/member")
    suspend fun getMyUserGraphQlInfo(
        @Header("Authorization") authorization: String,
        @Body body: GetMyUserGraphQLInfoRequestBody
    ): Response<ResponseBody>

    /**
     * 更新自己的使用者資訊
     */
    @RecordApi
    @POST("profile/graphql/mutation/member")
    suspend fun mutationMyUserGraphQlInfo(
        @Header("Authorization") authorization: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    /**
     *  依照使用者ID 取得暱稱及頭項資訊
     *
     *  @return 傳出ResponseBody，由外面在做解析
     */
    @RecordApi
    @POST("profile/graphql/query/members")
    suspend fun getUserGraphQLInfo(
        @Header("Authorization") authorization: String,
        @Body body: GetUserGraphQLInfoRequestBody
    ): Response<ResponseBody>

}
