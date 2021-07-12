package com.cmoney.backend2.profile.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.profile.data.GetNicknameAndAvatarResponse
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail.GetRegistrationCodeByEmailResponseBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone.GetRegistrationCodeByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.profile.service.api.getusergraphqlinfo.UserGraphQLInfo
import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData
import com.cmoney.backend2.profile.service.api.signupcompletebyemail.SignUpCompleteByEmailResponseBody
import com.cmoney.backend2.profile.service.api.signupcompletebyphone.SignUpCompleteByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.variable.GraphQLFieldDefinition
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ProfileWebImplTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

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
    fun getAccountTestSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun getAccountTestError() = mainCoroutineRule.runBlockingTest {
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
    fun sendVerificationEmailSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun sendVerificationEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun sendForgotPasswordEmailSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun sendForgotPasswordEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun sendVerificationSmsSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun sendVerificationSmsFailure() = mainCoroutineRule.runBlockingTest {
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
    fun checkCodeEmailSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun checkCodeEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun checkCodeSmsSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun checkCodeSmsFailure() = mainCoroutineRule.runBlockingTest {
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
    fun linkEmailSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun linkEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun linkPhoneSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun linkPhoneFailure() = mainCoroutineRule.runBlockingTest {
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
    fun linkFacebookSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun linkFacebookFailure() = mainCoroutineRule.runBlockingTest {
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
    fun linkContactEmailSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun linkContactEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun convertGuestByEmailSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun convertGuestByEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun convertGuestBySmsSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun convertGuestBySmsFailure() = mainCoroutineRule.runBlockingTest {
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
    fun changePasswordSuccess() = mainCoroutineRule.runBlockingTest {
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
    fun changePasswordFailure() = mainCoroutineRule.runBlockingTest {
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
    fun resetPasswordByEmailSuccess() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.resetPasswordByEmail(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.resetPasswordByEmail("code", "eamil@email.com", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetPasswordByEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun resetPasswordBySmsSuccess() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.resetPasswordBySms(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.resetPasswordBySms("code", "+886978850397", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetPasswordBySmsFailure() = mainCoroutineRule.runBlockingTest {
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
    fun signUpByEmail() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.signUpByEmail(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.signUpByEmail("mail@mail.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun signUpByEmailError() = mainCoroutineRule.runBlockingTest {
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
    fun signUpByPhone() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.signUpByPhone(body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.signUpByPhone("+886978850397")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun signUpByPhoneFailure() = mainCoroutineRule.runBlockingTest {
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
    fun getRegistrationCodeByEmail() = mainCoroutineRule.runBlockingTest {
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
    fun getRegistrationCodeByEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun getRegistrationCodeByPhone() = mainCoroutineRule.runBlockingTest {
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
    fun getRegistrationCodeByPhoneFailure() = mainCoroutineRule.runBlockingTest {
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
    fun signUpCompleteByEmail() = mainCoroutineRule.runBlockingTest {
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
    fun signUpCompleteByEmailFailure() = mainCoroutineRule.runBlockingTest {
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
    fun signUpCompleteByPhone() = mainCoroutineRule.runBlockingTest {
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
    fun signupCompleteByPhoneFailure() = mainCoroutineRule.runBlockingTest {
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
    fun `getMyUserGraphQlInfo 取得暱稱及頭像`() = mainCoroutineRule.runBlockingTest {
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
    fun `getMyUserGraphQlInfo 取得暱稱及頭像失敗`() = mainCoroutineRule.runBlockingTest {
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
    fun `mutationMyUserGraphQlInfo 更新暱稱及頭像`() = mainCoroutineRule.runBlockingTest {
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
    fun `mutationMyUserGraphQlInfo 更新暱稱及頭像失敗`() = mainCoroutineRule.runBlockingTest {
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
    fun `getNicknameAndAvatar 取得 id 清單的暱稱及頭像`() = mainCoroutineRule.runBlockingTest {
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
    fun `getNicknameAndAvatarFailure 取得 id 清單的暱稱及頭像失敗`() = mainCoroutineRule.runBlockingTest {
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

    @After
    fun tearDown() {
    }
}