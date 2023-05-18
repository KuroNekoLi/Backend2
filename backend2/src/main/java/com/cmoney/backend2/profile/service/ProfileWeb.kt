package com.cmoney.backend2.profile.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail.GetRegistrationCodeByEmailResponseBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone.GetRegistrationCodeByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData
import com.cmoney.backend2.profile.service.api.queryotherprofile.OtherMemberProfile
import com.cmoney.backend2.profile.service.api.queryotherprofile.OtherMemberProfileQueryBuilder
import com.cmoney.backend2.profile.service.api.queryprofile.MemberProfile
import com.cmoney.backend2.profile.service.api.queryprofile.MemberProfileQueryBuilder
import com.cmoney.backend2.profile.service.api.signupcompletebyemail.SignUpCompleteByEmailResponseBody
import com.cmoney.backend2.profile.service.api.signupcompletebyphone.SignUpCompleteByPhoneResponseBody

/**
 * Profile web
 */
interface ProfileWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得帳戶綁定資訊
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getAccount(
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account"
    ): Result<GetAccountResponseBody>

    /**
     * 送出驗證信
     *
     * @param email 信箱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun sendVerificationEmail(
        email: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/verification/email"
    ): Result<Unit>

    /**
     * 發送忘記密碼驗證信
     *
     * @param email 信箱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun sendForgotPasswordEmail(
        email: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/verification/forgotPassword/email"
    ): Result<Unit>

    /**
     * 送出驗證簡訊
     *
     * @param phone 電話
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun sendVerificationSms(
        phone: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/verification/sms"
    ): Result<Unit>

    /**
     * 確認信箱驗證碼是否有效
     *
     * @param email 信箱
     * @param code 驗證碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun checkCodeEmail(
        email: String,
        code: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/verification/code/Check/email"
    ): Result<Unit>

    /**
     * 確認簡訊驗證碼是否有效
     *
     * @param phone 電話
     * @param code 驗證碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun checkCodeSms(
        phone: String,
        code: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/verification/code/Check/sms"
    ): Result<Unit>

    /**
     * 信箱綁定
     *
     * @param code 驗證碼
     * @param email 信箱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun linkEmail(
        code: String,
        email: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/link/email"
    ): Result<Unit>

    /**
     * 手機綁定
     *
     * @param code 驗證碼
     * @param phone 信箱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun linkPhone(
        code: String,
        phone: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/link/cellphone"
    ): Result<Unit>

    /**
     * Facebook綁定
     *
     * @param token FB的授權Token
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun linkFacebook(
        token: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/link/facebook"
    ): Result<Unit>

    /**
     * 聯絡信箱綁定
     *
     * @param code 驗證碼
     * @param email 信箱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun linkContactEmail(
        code: String,
        email: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/link/contactEmail"
    ): Result<Unit>

    /**
     * 訪客帳號轉正綁定手機，此方法會更新訪客的密碼並將訪客綁定刪除
     *
     * @param email 信箱
     * @param code 驗證碼
     * @param newPassword 密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun convertGuestByEmail(
        email: String,
        code: String,
        newPassword: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/convertGuest/email"
    ): Result<Unit>

    /**
     * 訪客帳號轉正綁定信箱，此方法會更新訪客的密碼並將訪客綁定刪除
     *
     * @param phone 電話
     * @param code 驗證碼
     * @param newPassword 密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun convertGuestBySms(
        phone: String,
        code: String,
        newPassword: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/convertGuest/cellphone"
    ): Result<Unit>

    /**
     * 更改密碼
     *
     * @param oldPassword 舊密碼
     * @param newPassword 新密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/password/change"
    ): Result<Unit>

    /**
     * 信箱重置密碼
     *
     * @param code 驗證碼
     * @param email 信箱
     * @param newPassword 新密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun resetPasswordByEmail(
        code: String,
        email: String,
        newPassword: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/password/reset/email"
    ): Result<Unit>

    /**
     * 用簡訊重置密碼
     *
     * @param code 驗證碼
     * @param phone 電話
     * @param newPassword 新密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun resetPasswordBySms(
        code: String,
        phone: String,
        newPassword: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/account/password/reset/sms"
    ): Result<Unit>

    /**
     * 手機註冊
     *
     * @param email 信箱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun signUpByEmail(
        email: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/signup/email"
    ): Result<Unit>

    /**
     * 完成Email註冊程序
     *
     * @param phone 電話
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun signUpByPhone(
        phone: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/signup/cellphone"
    ): Result<Unit>

    /**
     * 完成Email註冊程序
     *
     * @param email 信箱
     * @param code 註冊碼
     * @param password 密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun signUpCompleteByEmail(
        email: String,
        code: String,
        password: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/signup/complete/email"
    ): Result<SignUpCompleteByEmailResponseBody>


    /**
     * 完成手機註冊程序
     *
     * @param phone 電話
     * @param code 註冊碼
     * @param password 密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun signUpCompleteByPhone(
        phone: String,
        code: String,
        password: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/signup/complete/cellphone"
    ): Result<SignUpCompleteByPhoneResponseBody>

    /**
     * 用信箱取得註冊碼
     *
     * @param code 註冊碼
     * @param email 信箱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getRegistrationCodeByEmail(
        code: String,
        email: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/signup/registrationcode/Get/email"
    ): Result<GetRegistrationCodeByEmailResponseBody>

    /**
     * 用簡訊取得註冊碼
     *
     * @param phone 電話
     * @param code 註冊碼
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getRegistrationCodeByPhone(
        code: String,
        phone: String,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/signup/registrationcode/Get/sms"
    ): Result<GetRegistrationCodeByPhoneResponseBody>

    /**
     * 取得自己的使用者資料
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @param block 取得時的設定
     * @return 會員資料
     */
    suspend fun getSelfMemberProfile(
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/graphql/query/member",
        block: MemberProfileQueryBuilder.() -> MemberProfileQueryBuilder,
    ): Result<MemberProfile>

    /**
     * 更新自己的使用者資料
     *
     * @param mutationData 更新的內容，欄位為null時不會上傳
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return Result.success 為成功，Result.failure 為失敗
     */
    suspend fun mutateMemberProfile(
        mutationData: MutationData,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/graphql/mutation/member"
    ): Result<Unit>

    /**
     * 取得其他使用者會員資訊
     *
     * @param memberIds 要查詢的使用者ID
     * @param domain 網域名稱
     * @param url 完整的Url
     * @param block 取得時的設定
     * @return 會員資料
     */
    suspend fun getOtherMemberProfiles(
        memberIds: List<Long>,
        domain: String = manager.getProfileSettingAdapter().getDomain(),
        url: String = "${domain}profile/graphql/query/members",
        block: OtherMemberProfileQueryBuilder.() -> OtherMemberProfileQueryBuilder
    ): Result<List<OtherMemberProfile>>
}