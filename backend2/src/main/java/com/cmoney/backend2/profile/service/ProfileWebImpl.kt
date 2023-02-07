package com.cmoney.backend2.profile.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.setting.Setting
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
import com.cmoney.backend2.profile.service.api.getusergraphqlinfo.UserGraphQLInfo
import com.cmoney.backend2.profile.service.api.linkcontactemail.LinkContactEmailRequestBody
import com.cmoney.backend2.profile.service.api.linkemail.LinkEmailRequestBody
import com.cmoney.backend2.profile.service.api.linkfacebook.LinkFacebookRequestBody
import com.cmoney.backend2.profile.service.api.linkphone.LinkPhoneRequestBody
import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData
import com.cmoney.backend2.profile.service.api.queryotherprofile.OtherMemberProfile
import com.cmoney.backend2.profile.service.api.queryotherprofile.OtherMemberProfileGraphQLRequestFieldsBuilder
import com.cmoney.backend2.profile.service.api.queryotherprofile.OtherMemberProfileQueryBuilder
import com.cmoney.backend2.profile.service.api.queryotherprofile.RawOtherMemberProfile
import com.cmoney.backend2.profile.service.api.queryprofile.MemberProfile
import com.cmoney.backend2.profile.service.api.queryprofile.MemberProfileGraphQLRequestFieldsBuilder
import com.cmoney.backend2.profile.service.api.queryprofile.MemberProfileQueryBuilder
import com.cmoney.backend2.profile.service.api.queryprofile.RawMemberProfile
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
import com.cmoney.backend2.profile.service.api.variable.GraphQLFieldDefinition
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.lang.reflect.Type

class ProfileWebImpl(
    private val gson: Gson,
    private val service: ProfileService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ProfileWeb {
    override suspend fun getAccount(): Result<GetAccountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getAccount(
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun sendVerificationEmail(email: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.sendVerificationEmail(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = SendVerificationEmailRequestBody(email)
                ).handleNoContent(gson)
            }
        }

    override suspend fun sendForgotPasswordEmail(email: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.sendForgotPasswordEmail(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = SendForgotPasswordEmailRequestBody(email)
                ).handleNoContent(gson)
            }
        }

    override suspend fun sendVerificationSms(phone: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.sendVerificationSms(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = SendVerificationSmsRequestBody(phone)
                ).handleNoContent(gson)
            }
        }

    override suspend fun checkCodeEmail(email: String, code: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.checkCodeEmail(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = CheckEmailCodeRequestBody(email, code)
                ).handleNoContent(gson)
            }
        }

    override suspend fun checkCodeSms(phone: String, code: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.checkCodeSms(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = CheckSmsCodeRequestBody(phone, code)
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkEmail(code: String, email: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkEmail(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = LinkEmailRequestBody(code, email)
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkPhone(code: String, phone: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkPhone(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = LinkPhoneRequestBody(code, phone)
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkFacebook(token: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkFacebook(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = LinkFacebookRequestBody(token)
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkContactEmail(code: String, email: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkContactEmail(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = LinkContactEmailRequestBody(code, email)
                ).handleNoContent(gson)
            }
        }

    override suspend fun convertGuestByEmail(
        email: String,
        code: String,
        newPassword: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.convertGuestByEmail(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ConvertGuestByEmailRequestBody(email, code, newPassword)
                ).handleNoContent(gson)
            }
        }

    override suspend fun convertGuestBySms(
        phone: String,
        code: String,
        newPassword: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.convertGuestBySms(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ConvertGuestBySmsRequestBody(phone, code, newPassword)
                ).handleNoContent(gson)
            }
        }


    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.changePassword(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ChangePasswordRequestBody(oldPassword, newPassword)
                ).handleNoContent(gson)
            }
        }

    override suspend fun resetPasswordByEmail(
        code: String,
        email: String,
        newPassword: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.resetPasswordByEmail(
                body = ResetPasswordByEmailRequestBody(code, email, newPassword)
            ).handleNoContent(gson)
        }
    }

    override suspend fun resetPasswordBySms(
        code: String,
        phone: String,
        newPassword: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.resetPasswordBySms(
                body = ResetPasswordBySmsRequestBody(code, phone, newPassword)
            ).handleNoContent(gson)
        }
    }

    override suspend fun signUpByEmail(email: String): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpByEmail(
                body = SignUpByEmailRequestBody(email)
            ).handleNoContent(gson)
        }
    }

    override suspend fun signUpByPhone(phone: String): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpByPhone(
                body = SignUpByPhoneRequestBody(phone)
            ).handleNoContent(gson)
        }
    }

    override suspend fun signUpCompleteByEmail(
        email: String,
        code: String,
        password: String
    ): Result<SignUpCompleteByEmailResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpCompleteByEmail(
                body = SignUpCompleteByEmailRequestBody(
                    email,
                    code,
                    password
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun signUpCompleteByPhone(
        phone: String,
        code: String,
        password: String
    ): Result<SignUpCompleteByPhoneResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpCompleteByPhone(
                body = SignupCompleteByPhoneRequestBody(
                    phone,
                    code,
                    password
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getRegistrationCodeByEmail(
        code: String,
        email: String
    ): Result<GetRegistrationCodeByEmailResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getRegistrationCodeByEmail(
                body = GetRegistrationCodeByEmailRequestBody(code, email)
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getRegistrationCodeByPhone(
        code: String,
        phone: String
    ): Result<GetRegistrationCodeByPhoneResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getRegistrationCodeByPhone(
                body = GetRegistrationCodeByPhoneRequestBody(
                    code, phone
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun <T> getMyUserGraphQlInfo(
        fields: Set<GraphQLFieldDefinition>,
        type: Type
    ): Result<T> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val responseBody = service.getMyUserGraphQlInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetMyUserGraphQLInfoRequestBody(
                    fields = "{ ${fields.joinToString(" ") { it.value }} }"
                )
            ).checkResponseBody(gson)

            return@runCatching gson.fromJson<T>(responseBody.string(), type)
        }
    }

    override suspend fun getSelfMemberProfile(
        block: MemberProfileQueryBuilder.() -> MemberProfileQueryBuilder
    ): Result<MemberProfile> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val params = MemberProfileQueryBuilder()
                    .block()
                    .build()
                val requestFields = MemberProfileGraphQLRequestFieldsBuilder(
                    queryParams = params
                )
                    .build()
                val responseBody = service.getMyUserGraphQlInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetMyUserGraphQLInfoRequestBody(fields = requestFields)
                ).checkResponseBody(gson)
                val rawMemberProfile =
                    gson.fromJson(responseBody.string(), RawMemberProfile::class.java)
                MemberProfile(
                    params = params,
                    id = setting.identityToken.getMemberId(),
                    raw = rawMemberProfile,
                )
            }
        }

    override suspend fun <T> mutationMyUserGraphQlInfo(
        variable: MutationData,
        type: Type
    ): Result<T> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            //因應後端問題 如果將某些沒有要修改欄位的欄位設為null 發出去  會導致api失敗 因此才用自己處理全部的requestBody
            val responseBody = service.mutationMyUserGraphQlInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = ("{\n" +
                    "  \"operationName\": \"updateMember\",\n" +
                    "  \"fields\": \"{ ${variable.getFieldsString()} }\",\n" +
                    "  \"variables\": " + variable.toJsonString() +
                    "\n}").toRequestBody("application/json".toMediaType())
            ).checkResponseBody(gson)

            return@runCatching gson.fromJson<T>(responseBody.string(), type)
        }
    }

    override suspend fun mutateMemberProfile(mutationData: MutationData): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.mutationMyUserGraphQlInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ("{\n" +
                        "  \"operationName\": \"updateMember\",\n" +
                        "  \"fields\": \"{ ${mutationData.getFieldsString()} }\",\n" +
                        "  \"variables\": " + mutationData.toJsonString() +
                        "\n}").toRequestBody("application/json".toMediaType())
                ).checkResponseBody(gson)
                Unit
            }
        }

    override suspend fun <T> getUserGraphQLInfo(
        memberIds: List<Long>,
        fields: Set<UserGraphQLInfo>,
        type: Type
    ): Result<List<T>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUserGraphQLInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetUserGraphQLInfoRequestBody(
                        memberIds,
                        "{ ${fields.joinToString(" ") { it.value }} }"
                    )
                )
                    .checkResponseBody(gson)
                    .parseResponseBodyToObject<T>(type)
            }
        }

    /**
     * @param type = TypeToken<List<T>> 要解析的格式
     */
    private fun <T> ResponseBody?.parseResponseBodyToObject(
        type: Type
    ): List<T> {
        return gson.fromJson<List<T>>(this?.string(), type)
    }

    override suspend fun getOtherMemberProfiles(
        memberIds: List<Long>,
        block: OtherMemberProfileQueryBuilder.() -> OtherMemberProfileQueryBuilder
    ): Result<List<OtherMemberProfile>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val params = OtherMemberProfileQueryBuilder()
                .block()
                .build()
            val requestFields = OtherMemberProfileGraphQLRequestFieldsBuilder(
                queryParams = params
            ).build()
            val requestBody = GetUserGraphQLInfoRequestBody(
                memberIds = memberIds,
                fields = requestFields
            )
            val responseBody = service.getUserGraphQLInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
            val type = object : TypeToken<List<RawOtherMemberProfile>>() {}.type
            val rawOtherProfiles =
                gson.fromJson<List<RawOtherMemberProfile>>(responseBody.string(), type)
            rawOtherProfiles.map { raw ->
                OtherMemberProfile(
                    params = params,
                    raw = raw
                )
            }
        }
    }

}