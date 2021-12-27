package com.cmoney.backend2.profile.service

import com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail.GetRegistrationCodeByEmailResponseBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone.GetRegistrationCodeByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.profile.service.api.getusergraphqlinfo.UserGraphQLInfo
import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData
import com.cmoney.backend2.profile.service.api.queryprofile.MemberProfile
import com.cmoney.backend2.profile.service.api.queryprofile.MemberProfileQueryBuilder
import com.cmoney.backend2.profile.service.api.signupcompletebyemail.SignUpCompleteByEmailResponseBody
import com.cmoney.backend2.profile.service.api.signupcompletebyphone.SignUpCompleteByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.variable.GraphQLFieldDefinition
import java.lang.reflect.Type

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
     */
    @Deprecated("Use getSelfMemberProfile to get MemberProfile")
    suspend fun <T> getMyUserGraphQlInfo(
        fields: Set<GraphQLFieldDefinition>,
        type: Type
    ): Result<T>

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
     * @param T 回傳資料型別
     * @param variable 更新的物件，使用[MutationData.Builder]創建
     * @param type 回傳解析的type
     * @return
     */
    @Deprecated("Can use mutateMemberProfile to avoid type alias")
    suspend fun <T> mutationMyUserGraphQlInfo(
        variable : MutationData,
        type: Type
    ): Result<T>

    suspend fun mutateMemberProfile(
        mutationData: MutationData
    ): Result<Unit>

    /**
     * 取得使用者的GraphQLInfo
     *
     * @param memberIds 要查詢的使用者ID
     * @param fields 要查詢的欄位
     * @param type Gson解析的資料格式(需先加上List<T>的解析) TypeToken<List<T>>(){}.type
     *
     * @return 查詢結果的資料清單 (會依照查詢欄位對應相關的Class）
     */
    suspend fun <T> getUserGraphQLInfo(
        memberIds: List<Long>,
        fields: Set<UserGraphQLInfo>,
        type: Type
    ): Result<List<T>>
}