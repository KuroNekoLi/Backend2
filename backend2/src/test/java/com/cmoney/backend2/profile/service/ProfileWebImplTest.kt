package com.cmoney.backend2.profile.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.profile.data.GetNicknameAndAvatarResponse
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail.GetRegistrationCodeByEmailResponseBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone.GetRegistrationCodeByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.profile.service.api.getusergraphqlinfo.UserGraphQLInfo
import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData
import com.cmoney.backend2.profile.service.api.queryotherprofile.RawOtherMemberProfile
import com.cmoney.backend2.profile.service.api.signupcompletebyemail.SignUpCompleteByEmailResponseBody
import com.cmoney.backend2.profile.service.api.signupcompletebyphone.SignUpCompleteByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.variable.GraphQLFieldDefinition
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ProfileWebImplTest {
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var service: ProfileService

    private val setting: Setting = TestSetting()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var webImpl: ProfileWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = ProfileWebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun getAccountTestSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.getAccount(
                authorization = any()
            )
        } returns Response.success(
            // language=JSON
            gson.fromJson(
                """{
  "email": "example@cmoney.com.tw",
  "contactEmail": "example@cmoney.com.tw",
  "cellphone": "+886912345678",
  "facebook": "example@cmoney.com.tw",
  "signupDate": "2019/10/14 上午 09:18:23"
}
""", GetAccountResponseBody::class.java
            )
        )
        val result = webImpl.getAccount()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getAccountTestError() = mainCoroutineRule.runTest {
        coEvery {
            service.getAccount(
                authorization = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )

        val result = webImpl.getAccount()
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun sendVerificationEmailSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.sendVerificationEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.sendVerificationEmail("email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun sendVerificationEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.sendVerificationEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.sendVerificationEmail("email@email.com")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun sendForgotPasswordEmailSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.sendForgotPasswordEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.sendForgotPasswordEmail("email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun sendForgotPasswordEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.sendForgotPasswordEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.sendForgotPasswordEmail("email@email.com")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun sendVerificationSmsSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.sendVerificationSms(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.sendVerificationSms("+886978850397")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun sendVerificationSmsFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.sendVerificationSms(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.sendVerificationSms("+886978850397")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun checkCodeEmailSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.checkCodeEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.checkCodeEmail("email@email.com", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun checkCodeEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.checkCodeEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.checkCodeEmail("email@email.com", "code")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun checkCodeSmsSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.checkCodeSms(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.checkCodeSms("+886978850397", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun checkCodeSmsFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.checkCodeSms(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.checkCodeSms("+886978850397", "code")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun linkEmailSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.linkEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkEmail("email@email.com", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.linkEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.linkEmail("email@email.com", "code")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun linkPhoneSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.linkPhone(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkPhone("+886978850397", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkPhoneFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.linkPhone(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.linkPhone("+886978850397", "code")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun linkFacebookSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.linkFacebook(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkFacebook("code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkFacebookFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.linkFacebook(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.linkFacebook("code")
        Truth.assertThat(result.isSuccess).isFalse()
    }


    @Test
    fun linkContactEmailSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.linkContactEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkContactEmail("code", "email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkContactEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.linkContactEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.linkContactEmail("code", "email@email.com")
        Truth.assertThat(result.isSuccess).isFalse()
    }


    @Test
    fun convertGuestByEmailSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.convertGuestByEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.convertGuestByEmail("email@email.com", "code", "newPasswrod")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun convertGuestByEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.convertGuestByEmail(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.convertGuestByEmail("email@email.com", "code", "newPasswrod")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun convertGuestBySmsSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.convertGuestBySms(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.convertGuestBySms("+886978850397", "code", "newPasswrod")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun convertGuestBySmsFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.convertGuestBySms(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.convertGuestBySms("+886978850397", "code", "newPasswrod")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun changePasswordSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.changePassword(
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.changePassword("oldPassword", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun changePasswordFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.changePassword(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.changePassword("oldPassword", "newPassword")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun resetPasswordByEmailSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.resetPasswordByEmail(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.resetPasswordByEmail("code", "eamil@email.com", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetPasswordByEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.resetPasswordByEmail(body = any())
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.resetPasswordByEmail("code", "eamil@email.com", "newPassword")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun resetPasswordBySmsSuccess() = mainCoroutineRule.runTest {
        coEvery {
            service.resetPasswordBySms(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.resetPasswordBySms("code", "+886978850397", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetPasswordBySmsFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.resetPasswordBySms(body = any())
        } returns Response.error(
            400, """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.resetPasswordBySms("code", "+886978850397", "newPassword")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun signUpByEmail() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpByEmail(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.signUpByEmail("mail@mail.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun signUpByEmailError() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpByEmail(body = any())
        } returns Response.error(
            400,
            // language=JSON
            """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.signUpByEmail("mail@this.is.mail.com")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun signUpByPhone() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpByPhone(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.signUpByPhone("+886978850397")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun signUpByPhoneFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpByPhone(body = any())
        } returns Response.error(
            400,
            // language=JSON
            """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.signUpByPhone("+886999999999")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getRegistrationCodeByEmail() = mainCoroutineRule.runTest {
        coEvery {
            service.getRegistrationCodeByEmail(body = any())
        } returns Response.success(
            200,
            gson.fromJson(
                // language=JSON
                """
                {
                  "registrationCode": "29345" 
                }
            """.trimIndent(),
                GetRegistrationCodeByEmailResponseBody::class.java
            )
        )
        val result = webImpl.getRegistrationCodeByEmail("code", "email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.registrationCode).isEqualTo("29345")
    }

    @Test
    fun getRegistrationCodeByEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.getRegistrationCodeByEmail(body = any())
        } returns Response.error(
            400,
            // language=JSON
            """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.getRegistrationCodeByEmail("code", "email@email.com")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getRegistrationCodeByPhone() = mainCoroutineRule.runTest {
        coEvery {
            service.getRegistrationCodeByPhone(body = any())
        } returns Response.success(
            200,
            gson.fromJson(
                // language=JSON
                """
                {
                  "registrationCode": "29345" 
                }
            """.trimIndent(),
                GetRegistrationCodeByPhoneResponseBody::class.java
            )
        )
        val result = webImpl.getRegistrationCodeByPhone("code", "email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.registrationCode).isEqualTo("29345")
    }

    @Test
    fun getRegistrationCodeByPhoneFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.getRegistrationCodeByPhone(body = any())
        } returns Response.error(
            400,
            // language=JSON
            """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.getRegistrationCodeByPhone("code", "email@email.com")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun signUpCompleteByEmail() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpCompleteByEmail(body = any())
        } returns Response.success(
            200,
            gson.fromJson(
                // language=JSON
                """
                {
                  "account": "mail@mail.com",
                  "password": "password"
                }
            """.trimIndent(),
                SignUpCompleteByEmailResponseBody
                ::class.java
            )
        )
        val result = webImpl.signUpCompleteByEmail("code", "email@email.com", "password")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.account).isEqualTo("mail@mail.com")
        Truth.assertThat(result.getOrNull()?.password).isEqualTo("password")
    }

    @Test
    fun signUpCompleteByEmailFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpCompleteByEmail(body = any())
        } returns Response.error(
            400,
            // language=JSON
            """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.signUpCompleteByEmail("code", "email@email.com", "password")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun signUpCompleteByPhone() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpCompleteByPhone(body = any())
        } returns Response.success(
            200,
            gson.fromJson(
                // language=JSON
                """
                {
                  "account": "+886978850555",
                  "password": "password"
                }
            """.trimIndent(),
                SignUpCompleteByPhoneResponseBody::class.java
            )
        )
        val result = webImpl.signUpCompleteByPhone("+886978850555", "code", "password")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.account).isEqualTo("+886978850555")
        Truth.assertThat(result.getOrNull()?.password).isEqualTo("password")
    }

    @Test
    fun signupCompleteByPhoneFailure() = mainCoroutineRule.runTest {
        coEvery {
            service.signUpCompleteByPhone(body = any())
        } returns Response.error(
            400,
            // language=JSON
            """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".toResponseBody()
        )
        val result = webImpl.signUpCompleteByPhone("+886978850555", "code", "password")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getMyUserGraphQlInfo 取得暱稱及頭像`() = mainCoroutineRule.runTest {
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.getMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            responseBody
        )

        val result = webImpl.getMyUserGraphQlInfo<GetNicknameAndAvatarResponse>(
            fields = setOf(
                GraphQLFieldDefinition.NickName,
                GraphQLFieldDefinition.Image
            ),
            type = object : TypeToken<GetNicknameAndAvatarResponse>() {}.type
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(result.exceptionOrNull()).isNull()
        Truth.assertThat(data.memberId).isNull()
        Truth.assertThat(data.nickname).isEqualTo("泰瑞瑞瑞瑞")
        Truth.assertThat(data.image)
            .isEqualTo("https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422")
    }

    @Test
    fun `getMyUserGraphQlInfo 取得暱稱及頭像失敗`() = mainCoroutineRule.runTest {
        coEvery {
            service.getMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400,
            // language=JSON
            """{
              "error": {
                "Code": 400,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = webImpl.getMyUserGraphQlInfo<GetNicknameAndAvatarResponse>(
            fields = setOf(
                GraphQLFieldDefinition.NickName,
                GraphQLFieldDefinition.Image
            ),
            type = object : TypeToken<GetNicknameAndAvatarResponse>() {}.type
        )
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
    }

    @Test
    fun `getSelfMemberProfile 取得暱稱及頭像`() = mainCoroutineRule.runTest {
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.getMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            responseBody
        )

        val result = webImpl.getSelfMemberProfile {
            nickname
            image
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(result.exceptionOrNull()).isNull()
        Truth.assertThat(data.id).isEmpty()
        Truth.assertThat(data.nickname).isEqualTo("泰瑞瑞瑞瑞")
        Truth.assertThat(data.image)
            .isEqualTo("https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422")
    }

    @Test
    fun `getSelfMemberProfile 取得暱稱及頭像失敗`() = mainCoroutineRule.runTest {
        coEvery {
            service.getMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400,
            // language=JSON
            """{
              "error": {
                "Code": 400,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = webImpl.getSelfMemberProfile {
            nickname
            image
        }
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
    }

    @Test
    fun `mutationMyUserGraphQlInfo 更新暱稱及頭像`() = mainCoroutineRule.runTest {
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.mutationMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            responseBody
        )

        val result = webImpl.mutationMyUserGraphQlInfo<GetNicknameAndAvatarResponse>(
            type = object : TypeToken<GetNicknameAndAvatarResponse>() {}.type,
            variable = MutationData.Builder(
                nickname = "泰瑞瑞瑞瑞",
                image = "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"
            ).build()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(result.exceptionOrNull()).isNull()
        Truth.assertThat(data.memberId).isNull()
        Truth.assertThat(data.nickname).isEqualTo("泰瑞瑞瑞瑞")
        Truth.assertThat(data.image)
            .isEqualTo("https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422")
    }

    @Test
    fun `mutationMyUserGraphQlInfo 更新暱稱及頭像失敗`() = mainCoroutineRule.runTest {
        coEvery {
            service.mutationMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400,
            // language=JSON
            """{
              "error": {
                "Code": 400,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = webImpl.mutationMyUserGraphQlInfo<GetNicknameAndAvatarResponse>(
            type = object : TypeToken<GetNicknameAndAvatarResponse>() {}.type,
            variable = MutationData.Builder(
                nickname = "泰瑞瑞瑞瑞",
                image = "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"
            ).build()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
    }

    @Test
    fun `mutateMemberProfile 更新暱稱及頭像`() = mainCoroutineRule.runTest {
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.mutationMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            responseBody
        )

        val result = webImpl.mutateMemberProfile(
            mutationData = MutationData.Builder(
                nickname = "泰瑞瑞瑞瑞",
                image = "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"
            ).build()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `mutateMemberProfile 更新暱稱及頭像失敗`() = mainCoroutineRule.runTest {
        coEvery {
            service.mutationMyUserGraphQlInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400,
            // language=JSON
            """{
              "error": {
                "Code": 400,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = webImpl.mutateMemberProfile(
            mutationData = MutationData.Builder(
                nickname = "泰瑞瑞瑞瑞",
                image = "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"
            ).build()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
    }

    @Test
    fun `getNicknameAndAvatar 取得 id 清單的暱稱及頭像`() = mainCoroutineRule.runTest {
        val responseBody =
            """[{"id": 1,"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}]""".toResponseBody()
        coEvery {
            service.getUserGraphQLInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            responseBody
        )

        val result = webImpl.getUserGraphQLInfo<GetNicknameAndAvatarResponse>(
            memberIds = listOf(1),
            fields = setOf(UserGraphQLInfo.ID, UserGraphQLInfo.Image, UserGraphQLInfo.NickName),
            type = object : TypeToken<List<GetNicknameAndAvatarResponse>>() {}.type
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isNullOrEmpty()).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNull()
        Truth.assertThat(data.firstOrNull()?.memberId).isEqualTo(1)
        Truth.assertThat(data.firstOrNull()?.nickname).isEqualTo("泰瑞瑞瑞瑞")
        Truth.assertThat(data.firstOrNull()?.image)
            .isEqualTo("https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422")
    }

    @Test
    fun `getNicknameAndAvatarFailure 取得 id 清單的暱稱及頭像失敗`() = mainCoroutineRule.runTest {
        coEvery {
            service.getUserGraphQLInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.error(
            400,
            // language=JSON
            """{
              "error": {
                "Code": 400,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = webImpl.getUserGraphQLInfo<GetNicknameAndAvatarResponse>(
            memberIds = listOf(1),
            fields = setOf(UserGraphQLInfo.ID, UserGraphQLInfo.Image, UserGraphQLInfo.NickName),
            type = object : TypeToken<List<GetNicknameAndAvatarResponse>>() {}.type
        )
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
    }

    @Test
    fun `getOtherMemberProfiles_取得成功`() = mainCoroutineRule.runTest {
        val memberId = 1L
        val testNickName = "測試帳號"
        val rawOtherMemberProfile = RawOtherMemberProfile(
            badges = listOf(),
            bio = null,
            communityRoles = listOf(),
            id = memberId,
            image = null,
            isBindingCellphone = null,
            level = null,
            nickname = testNickName
        )
        val rawOtherMemberProfiles = listOf(rawOtherMemberProfile)
        val responseBody = gson.toJson(rawOtherMemberProfiles).toResponseBody()
        coEvery {
            service.getUserGraphQLInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getOtherMemberProfiles(
            memberIds = listOf(memberId)
        ) {
            nickname
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val otherMemberProfiles = result.getOrNull()
        Truth.assertThat(otherMemberProfiles).isNotNull()
        requireNotNull(otherMemberProfiles)
        Truth.assertThat(otherMemberProfiles).hasSize(1)
        val otherMemberProfile = otherMemberProfiles.first()
        Truth.assertThat(otherMemberProfile.id).isEqualTo(memberId)
        Truth.assertThat(otherMemberProfile.nickname).isEqualTo(testNickName)
    }

    @Test
    fun `getOtherMemberProfiles_取得失敗_401`() = mainCoroutineRule.runTest {
        coEvery {
            service.getUserGraphQLInfo(
                authorization = any(),
                body = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = webImpl.getOtherMemberProfiles(
            memberIds = listOf(1)
        ) {
            nickname
        }
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @After
    fun tearDown() {
    }
}