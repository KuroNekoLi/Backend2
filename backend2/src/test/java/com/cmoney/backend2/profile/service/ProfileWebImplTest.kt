package com.cmoney.backend2.profile.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail.GetRegistrationCodeByEmailResponseBody
import com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone.GetRegistrationCodeByPhoneResponseBody
import com.cmoney.backend2.profile.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData
import com.cmoney.backend2.profile.service.api.queryotherprofile.RawOtherMemberProfile
import com.cmoney.backend2.profile.service.api.signupcompletebyemail.SignUpCompleteByEmailResponseBody
import com.cmoney.backend2.profile.service.api.signupcompletebyphone.SignUpCompleteByPhoneResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
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
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: ProfileService

    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var webImpl: ProfileWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = ProfileWebImpl(
            manager = manager,
            service = service,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getProfileSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getAccountTest_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account"
        val urlSlot = slot<String>()
        coEvery {
            service.getAccount(
                url = capture(urlSlot),
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
        webImpl.getAccount()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAccountTest_success() = testScope.runTest {
        coEvery {
            service.getAccount(
                url = any(),
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
    fun getAccountTestError() = testScope.runTest {
        coEvery {
            service.getAccount(
                url = any(),
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
    fun `sendVerificationEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/verification/email"
        val urlSlot = slot<String>()
        coEvery {
            service.sendVerificationEmail(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.sendVerificationEmail("email@email.com")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun sendVerificationEmail_success() = testScope.runTest {
        coEvery {
            service.sendVerificationEmail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.sendVerificationEmail("email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun sendVerificationEmail_failure() = testScope.runTest {
        coEvery {
            service.sendVerificationEmail(
                url = any(),
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
    fun `sendForgotPasswordEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/verification/forgotPassword/email"
        val urlSlot = slot<String>()
        coEvery {
            service.sendForgotPasswordEmail(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.sendForgotPasswordEmail("email@email.com")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun sendForgotPasswordEmail_success() = testScope.runTest {
        coEvery {
            service.sendForgotPasswordEmail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.sendForgotPasswordEmail("email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun sendForgotPasswordEmail_failure() = testScope.runTest {
        coEvery {
            service.sendForgotPasswordEmail(
                url = any(),
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
    fun `sendVerificationSms_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/verification/sms"
        val urlSlot = slot<String>()
        coEvery {
            service.sendVerificationSms(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.sendVerificationSms("+886978850397")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun sendVerificationSms_success() = testScope.runTest {
        coEvery {
            service.sendVerificationSms(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.sendVerificationSms("+886978850397")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun sendVerificationSms_failure() = testScope.runTest {
        coEvery {
            service.sendVerificationSms(
                url = any(),
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
    fun `checkCodeEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/verification/code/Check/email"
        val urlSlot = slot<String>()
        coEvery {
            service.checkCodeEmail(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.checkCodeEmail("email@email.com", "code")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun checkCodeEmail_success() = testScope.runTest {
        coEvery {
            service.checkCodeEmail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.checkCodeEmail("email@email.com", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun checkCodeEmail_failure() = testScope.runTest {
        coEvery {
            service.checkCodeEmail(
                url = any(),
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
    fun `checkCodeSms_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/verification/code/Check/sms"
        val urlSlot = slot<String>()
        coEvery {
            service.checkCodeSms(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.checkCodeSms("+886978850397", "code")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun checkCodeSms_success() = testScope.runTest {
        coEvery {
            service.checkCodeSms(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.checkCodeSms("+886978850397", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun checkCodeSms_failure() = testScope.runTest {
        coEvery {
            service.checkCodeSms(
                url = any(),
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
    fun `linkEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/link/email"
        val urlSlot = slot<String>()
        coEvery {
            service.linkEmail(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.linkEmail("email@email.com", "code")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun linkEmail_success() = testScope.runTest {
        coEvery {
            service.linkEmail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkEmail("email@email.com", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkEmail_failure() = testScope.runTest {
        coEvery {
            service.linkEmail(
                url = any(),
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
    fun `linkPhone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/link/cellphone"
        val urlSlot = slot<String>()
        coEvery {
            service.linkPhone(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.linkPhone("+886978850397", "code")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun linkPhone_success() = testScope.runTest {
        coEvery {
            service.linkPhone(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkPhone("+886978850397", "code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkPhone_failure() = testScope.runTest {
        coEvery {
            service.linkPhone(
                url = any(),
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
    fun `linkFacebook_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/link/facebook"
        val urlSlot = slot<String>()
        coEvery {
            service.linkFacebook(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.linkFacebook("code")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun linkFacebook_success() = testScope.runTest {
        coEvery {
            service.linkFacebook(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkFacebook("code")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkFacebook_failure() = testScope.runTest {
        coEvery {
            service.linkFacebook(
                url = any(),
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
    fun `linkContactEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/link/contactEmail"
        val urlSlot = slot<String>()
        coEvery {
            service.linkContactEmail(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.linkContactEmail("code", "email@email.com")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun linkContactEmail_success() = testScope.runTest {
        coEvery {
            service.linkContactEmail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.linkContactEmail("code", "email@email.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun linkContactEmail_failure() = testScope.runTest {
        coEvery {
            service.linkContactEmail(
                url = any(),
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
    fun `convertGuestByEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/convertGuest/email"
        val urlSlot = slot<String>()
        coEvery {
            service.convertGuestByEmail(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.convertGuestByEmail("email@email.com", "code", "newPasswrod")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun convertGuestByEmail_success() = testScope.runTest {
        coEvery {
            service.convertGuestByEmail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.convertGuestByEmail("email@email.com", "code", "newPasswrod")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun convertGuestByEmail_failure() = testScope.runTest {
        coEvery {
            service.convertGuestByEmail(
                url = any(),
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
    fun `convertGuestBySms_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/convertGuest/cellphone"
        val urlSlot = slot<String>()
        coEvery {
            service.convertGuestBySms(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.convertGuestBySms("+886978850397", "code", "newPasswrod")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun convertGuestBySms_success() = testScope.runTest {
        coEvery {
            service.convertGuestBySms(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.convertGuestBySms("+886978850397", "code", "newPasswrod")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun convertGuestBySms_failure() = testScope.runTest {
        coEvery {
            service.convertGuestBySms(
                url = any(),
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
    fun `changePassword_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/password/change"
        val urlSlot = slot<String>()
        coEvery {
            service.changePassword(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        webImpl.changePassword("oldPassword", "newPassword")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun changePassword_success() = testScope.runTest {
        coEvery {
            service.changePassword(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(204, null as? Void)
        val result = webImpl.changePassword("oldPassword", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun changePassword_failure() = testScope.runTest {
        coEvery {
            service.changePassword(
                url = any(),
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
    fun `resetPasswordByEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/password/reset/email"
        val urlSlot = slot<String>()
        coEvery {
            service.resetPasswordByEmail(url = capture(urlSlot), body = any())
        } returns Response.success(204, null as? Void)
        webImpl.resetPasswordByEmail("code", "eamil@email.com", "newPassword")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun resetPasswordByEmail_success() = testScope.runTest {
        coEvery {
            service.resetPasswordByEmail(url = any(), body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.resetPasswordByEmail("code", "eamil@email.com", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetPasswordByEmail_failure() = testScope.runTest {
        coEvery {
            service.resetPasswordByEmail(url = any(), body = any())
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
    fun `resetPasswordBySms_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/account/password/reset/sms"
        val urlSlot = slot<String>()
        coEvery {
            service.resetPasswordBySms(url = capture(urlSlot), body = any())
        } returns Response.success(204, null as? Void)
        webImpl.resetPasswordBySms("code", "+886978850397", "newPassword")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun resetPasswordBySms_success() = testScope.runTest {
        coEvery {
            service.resetPasswordBySms(url = any(), body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.resetPasswordBySms("code", "+886978850397", "newPassword")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetPasswordBySms_failure() = testScope.runTest {
        coEvery {
            service.resetPasswordBySms(url = any(), body = any())
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
    fun `signUpByEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/signup/email"
        val urlSlot = slot<String>()
        coEvery {
            service.signUpByEmail(url = capture(urlSlot), body = any())
        } returns Response.success(204, null as? Void)
        webImpl.signUpByEmail("mail@mail.com")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun signUpByEmail_success() = testScope.runTest {
        coEvery {
            service.signUpByEmail(url = any(), body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.signUpByEmail("mail@mail.com")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun signUpByEmail_failure() = testScope.runTest {
        coEvery {
            service.signUpByEmail(url = any(), body = any())
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
    fun `signUpByPhone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/signup/cellphone"
        val urlSlot = slot<String>()
        coEvery {
            service.signUpByPhone(url = capture(urlSlot), body = any())
        } returns Response.success(204, null as? Void)
        webImpl.signUpByPhone("+886978850397")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun signUpByPhone_success() = testScope.runTest {
        coEvery {
            service.signUpByPhone(url = any(), body = any())
        } returns Response.success(204, null as? Void)
        val result = webImpl.signUpByPhone("+886978850397")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun signUpByPhone_failure() = testScope.runTest {
        coEvery {
            service.signUpByPhone(url = any(), body = any())
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
    fun `getRegistrationCodeByEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/signup/registrationcode/Get/email"
        val urlSlot = slot<String>()
        coEvery {
            service.getRegistrationCodeByEmail(url = capture(urlSlot), body = any())
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
        webImpl.getRegistrationCodeByEmail("code", "email@email.com")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRegistrationCodeByEmail_success() = testScope.runTest {
        coEvery {
            service.getRegistrationCodeByEmail(url = any(), body = any())
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
    fun getRegistrationCodeByEmail_failure() = testScope.runTest {
        coEvery {
            service.getRegistrationCodeByEmail(url = any(), body = any())
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
    fun `getRegistrationCodeByPhone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/signup/registrationcode/Get/sms"
        val urlSlot = slot<String>()
        coEvery {
            service.getRegistrationCodeByPhone(url = capture(urlSlot), body = any())
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
        webImpl.getRegistrationCodeByPhone("code", "email@email.com")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRegistrationCodeByPhone_success() = testScope.runTest {
        coEvery {
            service.getRegistrationCodeByPhone(url = any(), body = any())
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
    fun getRegistrationCodeByPhone_failure() = testScope.runTest {
        coEvery {
            service.getRegistrationCodeByPhone(url = any(), body = any())
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
    fun `signUpCompleteByEmail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/signup/complete/email"
        val urlSlot = slot<String>()
        coEvery {
            service.signUpCompleteByEmail(url = capture(urlSlot), body = any())
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
        webImpl.signUpCompleteByEmail("code", "email@email.com", "password")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun signUpCompleteByEmail() = testScope.runTest {
        coEvery {
            service.signUpCompleteByEmail(url = any(), body = any())
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
    fun signUpCompleteByEmail_failure() = testScope.runTest {
        coEvery {
            service.signUpCompleteByEmail(url = any(), body = any())
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
    fun `signUpCompleteByPhone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/signup/complete/cellphone"
        val urlSlot = slot<String>()
        coEvery {
            service.signUpCompleteByPhone(url = capture(urlSlot), body = any())
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
        webImpl.signUpCompleteByPhone("+886978850555", "code", "password")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun signUpCompleteByPhone_success() = testScope.runTest {
        coEvery {
            service.signUpCompleteByPhone(url = any(), body = any())
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
    fun signupCompleteByPhone_failure() = testScope.runTest {
        coEvery {
            service.signUpCompleteByPhone(url = any(), body = any())
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
    fun `getSelfMemberProfile_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/graphql/query/member"
        val urlSlot = slot<String>()
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.getMyUserGraphQlInfo(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            responseBody
        )

        webImpl.getSelfMemberProfile {
            nickname
            image
        }
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSelfMemberProfile_success() = testScope.runTest {
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.getMyUserGraphQlInfo(
                url = any(),
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
    fun getSelfMemberProfile_failure() = testScope.runTest {
        coEvery {
            service.getMyUserGraphQlInfo(
                url = any(),
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
    fun `mutateMemberProfile_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/graphql/mutation/member"
        val urlSlot = slot<String>()
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.mutationMyUserGraphQlInfo(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            responseBody
        )

        webImpl.mutateMemberProfile(
            mutationData = MutationData.Builder(
                nickname = "泰瑞瑞瑞瑞",
                image = "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"
            ).build()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun mutateMemberProfile_success() = testScope.runTest {
        val responseBody =
            """{"nickname": "泰瑞瑞瑞瑞","image": "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422"}""".toResponseBody()
        coEvery {
            service.mutationMyUserGraphQlInfo(
                url = any(),
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
    fun mutateMemberProfile_failure() = testScope.runTest {
        coEvery {
            service.mutationMyUserGraphQlInfo(
                url = any(),
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
    fun `getOtherMemberProfiles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}profile/graphql/query/members"
        val urlSlot = slot<String>()
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
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        webImpl.getOtherMemberProfiles(
            memberIds = listOf(memberId)
        ) {
            nickname
        }
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getOtherMemberProfiles_success() = testScope.runTest {
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
                url = any(),
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
    fun getOtherMemberProfiles_failure_401() = testScope.runTest {
        coEvery {
            service.getUserGraphQLInfo(
                url = any(),
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
        unmockkAll()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}