package com.cmoney.backend2.profile.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class ProfileWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: ProfileService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ProfileWeb {
    override suspend fun getAccount(
        domain: String,
        url: String
    ): Result<GetAccountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getAccount(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun sendVerificationEmail(
        email: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.sendVerificationEmail(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = SendVerificationEmailRequestBody(email = email)
                ).handleNoContent(gson)
            }
        }

    override suspend fun sendForgotPasswordEmail(
        email: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.sendForgotPasswordEmail(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = SendForgotPasswordEmailRequestBody(email = email)
                ).handleNoContent(gson)
            }
        }

    override suspend fun sendVerificationSms(
        phone: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.sendVerificationSms(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = SendVerificationSmsRequestBody(phone = phone)
                ).handleNoContent(gson)
            }
        }

    override suspend fun checkCodeEmail(
        email: String,
        code: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.checkCodeEmail(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = CheckEmailCodeRequestBody(
                        email = email,
                        code = code
                    )
                ).handleNoContent(gson)
            }
        }

    override suspend fun checkCodeSms(
        phone: String,
        code: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.checkCodeSms(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = CheckSmsCodeRequestBody(
                        cellphone = phone,
                        code = code
                    )
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkEmail(
        code: String,
        email: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkEmail(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = LinkEmailRequestBody(
                        code = code,
                        email = email
                    )
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkPhone(
        code: String,
        phone: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkPhone(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = LinkPhoneRequestBody(
                        code = code,
                        cellphone = phone
                    )
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkFacebook(
        token: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkFacebook(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = LinkFacebookRequestBody(token = token)
                ).handleNoContent(gson)
            }
        }

    override suspend fun linkContactEmail(
        code: String,
        email: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.linkContactEmail(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = LinkContactEmailRequestBody(
                        code = code,
                        email = email
                    )
                ).handleNoContent(gson)
            }
        }

    override suspend fun convertGuestByEmail(
        email: String,
        code: String,
        newPassword: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.convertGuestByEmail(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = ConvertGuestByEmailRequestBody(
                        account = email,
                        code = code,
                        newPassword = newPassword
                    )
                ).handleNoContent(gson)
            }
        }

    override suspend fun convertGuestBySms(
        phone: String,
        code: String,
        newPassword: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.convertGuestBySms(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = ConvertGuestBySmsRequestBody(
                        account = phone,
                        code = code,
                        newPassword = newPassword
                    )
                ).handleNoContent(gson)
            }
        }


    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.changePassword(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = ChangePasswordRequestBody(
                        oldPassword = oldPassword,
                        newPassword = newPassword
                    )
                ).handleNoContent(gson)
            }
        }

    override suspend fun resetPasswordByEmail(
        code: String,
        email: String,
        newPassword: String,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.resetPasswordByEmail(
                url = url,
                body = ResetPasswordByEmailRequestBody(
                    code = code,
                    email = email,
                    newPassword = newPassword
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun resetPasswordBySms(
        code: String,
        phone: String,
        newPassword: String,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.resetPasswordBySms(
                url = url,
                body = ResetPasswordBySmsRequestBody(
                    code = code,
                    cellphone = phone,
                    newPassword = newPassword
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun signUpByEmail(
        email: String,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpByEmail(
                url = url,
                body = SignUpByEmailRequestBody(email = email)
            ).handleNoContent(gson)
        }
    }

    override suspend fun signUpByPhone(
        phone: String,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpByPhone(
                url = url,
                body = SignUpByPhoneRequestBody(cellphone = phone)
            ).handleNoContent(gson)
        }
    }

    override suspend fun signUpCompleteByEmail(
        email: String,
        code: String,
        password: String,
        domain: String,
        url: String
    ): Result<SignUpCompleteByEmailResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpCompleteByEmail(
                url = url,
                body = SignUpCompleteByEmailRequestBody(
                    email = email,
                    code = code,
                    password = password
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun signUpCompleteByPhone(
        phone: String,
        code: String,
        password: String,
        domain: String,
        url: String
    ): Result<SignUpCompleteByPhoneResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.signUpCompleteByPhone(
                url = url,
                body = SignupCompleteByPhoneRequestBody(
                    cellphone = phone,
                    code = code,
                    password = password
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getRegistrationCodeByEmail(
        code: String,
        email: String,
        domain: String,
        url: String
    ): Result<GetRegistrationCodeByEmailResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getRegistrationCodeByEmail(
                url = url,
                body = GetRegistrationCodeByEmailRequestBody(
                    code = code,
                    email = email
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getRegistrationCodeByPhone(
        code: String,
        phone: String,
        domain: String,
        url: String
    ): Result<GetRegistrationCodeByPhoneResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getRegistrationCodeByPhone(
                url = url,
                body = GetRegistrationCodeByPhoneRequestBody(
                    code = code,
                    cellphone = phone
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getSelfMemberProfile(
        domain: String,
        url: String,
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
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = GetMyUserGraphQLInfoRequestBody(fields = requestFields)
                ).checkResponseBody(gson)
                val rawMemberProfile =
                    gson.fromJson(responseBody.string(), RawMemberProfile::class.java)
                MemberProfile(
                    params = params,
                    id = manager.getIdentityToken().getMemberId(),
                    raw = rawMemberProfile,
                )
            }
        }

    override suspend fun mutateMemberProfile(
        mutationData: MutationData,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.mutationMyUserGraphQlInfo(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = ("{\n" +
                        "  \"operationName\": \"updateMember\",\n" +
                        "  \"fields\": \"{ ${mutationData.getFieldsString()} }\",\n" +
                        "  \"variables\": " + mutationData.toJsonString() +
                        "\n}").toRequestBody("application/json".toMediaType())
                ).checkResponseBody(gson)
                Unit
            }
        }

    override suspend fun getOtherMemberProfiles(
        memberIds: List<Long>,
        domain: String,
        url: String,
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
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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