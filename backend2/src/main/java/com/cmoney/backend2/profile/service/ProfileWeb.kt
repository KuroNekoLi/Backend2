package com.cmoney.backend2.profile.service

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
    /**
     * 取得帳戶綁定資訊
     */
    suspend fun getAccount(): Result<GetAccountResponseBody>

    /**
     * 送出驗證信
     *
     * @param email 信箱
     */
    suspend fun sendVerificationEmail(email: String): Result<Unit>

    /**
     * 發送忘記密碼驗證信
     *
     * @param email 信箱
     */
    suspend fun sendForgotPasswordEmail(email: String): Result<Unit>

    /**
     * 送出驗證簡訊
     *
     * @param phone 電話
     */
    suspend fun sendVerificationSms(phone: String): Result<Unit>

    /**
     * 確認信箱驗證碼是否有效
     *
     * @param email 信箱
     * @param code 驗證碼
     */
    suspend fun checkCodeEmail(email: String, code: String): Result<Unit>

    /**
     * 確認簡訊驗證碼是否有效
     *
     * @param phone 電話
     * @param code 驗證碼
     */
    suspend fun checkCodeSms(phone: String, code: String): Result<Unit>

    /**
     * 信箱綁定
     *
     * @param code 驗證碼
     * @param email 信箱
     */
    suspend fun linkEmail(code: String, email: String): Result<Unit>

    /**
     * 手機綁定
     *
     * @param code 驗證碼
     * @param phone 信箱
     */
    suspend fun linkPhone(code: String, phone: String): Result<Unit>

    /**
     * Facebook綁定
     *
     * @param token FB的授權Token
     */
    suspend fun linkFacebook(token: String): Result<Unit>

    /**
     * 聯絡信箱綁定
     *
     * @param code 驗證碼
     * @param email 信箱
     */
    suspend fun linkContactEmail(code: String, email: String): Result<Unit>

    /**
     * 訪客帳號轉正綁定手機，此方法會更新訪客的密碼並將訪客綁定刪除
     *
     * @param email 信箱
     * @param code 驗證碼
     * @param newPassword 密碼
     */
    suspend fun convertGuestByEmail(email: String, code: String, newPassword: String): Result<Unit>

    /**
     * 訪客帳號轉正綁定信箱，此方法會更新訪客的密碼並將訪客綁定刪除
     *
     * @param phone 電話
     * @param code 驗證碼
     * @param newPassword 密碼
     */
    suspend fun convertGuestBySms(phone: String, code: String, newPassword: String): Result<Unit>

    /**
     * 更改密碼
     *
     * @param oldPassword 舊密碼
     * @param newPassword 新密碼
     */
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit>

    /**
     * 信箱重置密碼
     *
     * @param code 驗證碼
     * @param email 信箱
     * @param newPassword 新密碼
     */
    suspend fun resetPasswordByEmail(
        code: String,
        email: String,
        newPassword: String
    ): Result<Unit>

    /**
     * 用簡訊重置密碼
     *
     * @param code 驗證碼
     * @param phone 電話
     * @param newPassword 新密碼
     */
    suspend fun resetPasswordBySms(
        code: String,
        phone: String,
        newPassword: String
    ): Result<Unit>

    /**
     * 手機註冊
     *
     * @param email 信箱
     */
    suspend fun signUpByEmail(email: String): Result<Unit>

    /**
     * 完成Email註冊程序
     *
     * @param phone 電話
     */
    suspend fun signUpByPhone(phone: String): Result<Unit>

    /**
     * 完成Email註冊程序
     *
     * @param email 信箱
     * @param code 註冊碼
     * @param password 密碼
     */
    suspend fun signUpCompleteByEmail(
        email: String,
        code: String,
        password: String
    ): Result<SignUpCompleteByEmailResponseBody>


    /**
     * 完成手機註冊程序
     *
     * @param phone 電話
     * @param code 註冊碼
     * @param password 密碼
     */
    suspend fun signUpCompleteByPhone(
        phone: String,
        code: String,
        password: String
    ): Result<SignUpCompleteByPhoneResponseBody>

    /**
     * 用信箱取得註冊碼
     *
     * @param code 註冊碼
     * @param email 信箱
     */
    suspend fun getRegistrationCodeByEmail(
        code: String,
        email: String
    ): Result<GetRegistrationCodeByEmailResponseBody>

    /**
     * 用簡訊取得註冊碼
     *
     * @param phone 電話
     * @param code 註冊碼
     */
    suspend fun getRegistrationCodeByPhone(
        code: String,
        phone: String
    ): Result<GetRegistrationCodeByPhoneResponseBody>

    /**
     * 取得自己的使用者資料
     *
     * @param block 取得時的設定
     * @return 會員資料
     */
    suspend fun getSelfMemberProfile(
        block: MemberProfileQueryBuilder.() -> MemberProfileQueryBuilder
    ): Result<MemberProfile>

    /**
     * 更新自己的使用者資料
     *
     * @param mutationData 更新的內容，欄位為null時不會上傳
     * @return Result.success 為成功，Result.failure 為失敗
     */
    suspend fun mutateMemberProfile(
        mutationData: MutationData
    ): Result<Unit>

    /**
     * 取得其他使用者會員資訊
     *
     * @param memberIds 要查詢的使用者ID
     * @param block 取得時的設定
     * @return 會員資料
     */
    suspend fun getOtherMemberProfiles(
        memberIds: List<Long>,
        block: OtherMemberProfileQueryBuilder.() -> OtherMemberProfileQueryBuilder
    ): Result<List<OtherMemberProfile>>
}