package com.cmoney.backend2.cellphone.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.cellphone.service.api.CellphoneParam
import com.cmoney.backend2.cellphone.service.api.bindcellphone.BindCellphoneResponseBodyWithError
import com.cmoney.backend2.cellphone.service.api.checkcellphonebindingverifycode.CheckCellphoneBindingVerifyCodeResponseBodyWithError
import com.cmoney.backend2.cellphone.service.api.checkverifycode.CellphoneCheckVerifyCodeWithError
import com.cmoney.backend2.cellphone.service.api.forgotpassword.CellphoneForgotPasswordWithError
import com.cmoney.backend2.cellphone.service.api.getaccountinfo.AccountInfoWithError
import com.cmoney.backend2.cellphone.service.api.getverifycode.CellphoneGetVerifyCodeWithError
import com.cmoney.backend2.cellphone.service.api.register.CellphoneRegisterWithError
import com.cmoney.backend2.cellphone.service.api.unbindcellphone.UnbindCellphoneResponseBodyWithError
import com.cmoney.backend2.cellphone.service.api.updatepassword.UpdatePasswordResponseBodyWithError
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CellphoneWebImplTest {

    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)
    @MockK
    private lateinit var service: CellphoneService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: CellphoneWeb
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = CellphoneWebImpl(
            gson = gson,
            service = service,
            manager = manager,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getCellphoneSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getVerifyCode_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = CellphoneGetVerifyCodeWithError(
            verifyCodeDuration = 300,
            verifyCodeResendInterval = 300
        )
        coEvery {
            service.getVerifyCode(
                url = capture(urlSlot),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)
        web.getVerifyCode(CellphoneParam("", ""))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getVerifyCode_success() = testScope.runTest {
        val responseBody = CellphoneGetVerifyCodeWithError(
            verifyCodeDuration = 300,
            verifyCodeResendInterval = 300
        )
        coEvery {
            service.getVerifyCode(
                url = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(),
            )
        } returns Response.success(responseBody)

        val result = web.getVerifyCode(CellphoneParam("", ""))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.verifyCodeDuration).isEqualTo(300)
        Truth.assertThat(data.verifyCodeResendInterval).isEqualTo(300)
    }

    @Test(expected = ServerException::class)
    fun getVerifyCode_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"手機號碼轉換錯誤"}}"""
        val responseBody =
            gson.fromJson(responseBodyJson, CellphoneGetVerifyCodeWithError::class.java)
        coEvery {
            service.getVerifyCode(
                url = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(),
            )
        } returns Response.success(responseBody)

        val result = web.getVerifyCode(CellphoneParam("", ""))
        result.getOrThrow()
    }

    @Test
    fun `checkVerifyCode_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = CellphoneCheckVerifyCodeWithError(isSuccess = true)
        coEvery {
            service.checkVerifyCode(
                url = capture(urlSlot),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(),
                verifyCode = "123"
            )
        } returns Response.success(responseBody)
        web.checkVerifyCode(CellphoneParam("", ""), "123")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun checkVerifyCode_success() = testScope.runTest {
        val responseBody = CellphoneCheckVerifyCodeWithError(isSuccess = true)
        coEvery {
            service.checkVerifyCode(
                url = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(),
                verifyCode = "123"
            )
        } returns Response.success(responseBody)

        val result = web.checkVerifyCode(CellphoneParam("", ""), "123")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun checkVerifyCode_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":2,"Message":"驗證碼有誤"}}"""
        val responseBody =
            gson.fromJson(responseBodyJson, CellphoneCheckVerifyCodeWithError::class.java)
        coEvery {
            service.checkVerifyCode(
                url = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(),
                verifyCode = any()
            )
        } returns Response.success(responseBody)

        val result = web.checkVerifyCode(CellphoneParam("", ""), "123")
        result.getOrThrow()
    }

    @Test
    fun `registerByCellphone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = CellphoneRegisterWithError(isSuccess = true)
        coEvery {
            service.registerByCellphone(
                url = capture(urlSlot),
                xApiLog = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(), password = any(), platform = any()
            )
        } returns Response.success(responseBody)

        web.registerByCellphone(CellphoneParam("", ""), "123")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun registerByCellphone_success() = testScope.runTest {
        val responseBody = CellphoneRegisterWithError(isSuccess = true)
        coEvery {
            service.registerByCellphone(
                url = any(),
                xApiLog = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(), password = any(), platform = any()
            )
        } returns Response.success(responseBody)

        val result = web.registerByCellphone(CellphoneParam("", ""), "123")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun registerByCellphone_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"手機號碼轉換錯誤"}}"""
        val responseBody = gson.fromJson(responseBodyJson, CellphoneRegisterWithError::class.java)
        coEvery {
            service.registerByCellphone(
                url = any(),
                xApiLog = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any(), password = any(), platform = any()
            )
        } returns Response.success(responseBody)

        val result = web.registerByCellphone(CellphoneParam("", ""), "123")
        result.getOrThrow()
    }

    @Test
    fun `forgotPasswordForCellphone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = CellphoneForgotPasswordWithError(isSuccess = true)
        coEvery {
            service.forgotPasswordForCellphone(
                url = capture(urlSlot),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)

        web.forgotPasswordForCellphone(CellphoneParam("", ""))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun forgotPasswordForCellphone_success() = testScope.runTest {
        val responseBody = CellphoneForgotPasswordWithError(isSuccess = true)
        coEvery {
            service.forgotPasswordForCellphone(
                url = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)

        val result = web.forgotPasswordForCellphone(CellphoneParam("", ""))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun forgotPasswordForCellphone_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"手機號碼轉換錯誤"}}"""
        val responseBody =
            gson.fromJson(responseBodyJson, CellphoneForgotPasswordWithError::class.java)
        coEvery {
            service.forgotPasswordForCellphone(
                url = any(),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)

        val result = web.forgotPasswordForCellphone(CellphoneParam("", ""))
        result.getOrThrow()
    }

    @Test
    fun `updatePassword_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = CellphoneForgotPasswordWithError(isSuccess = true)
        coEvery {
            service.forgotPasswordForCellphone(
                url = capture(urlSlot),
                action = any(),
                countryCode = any(),
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)

        web.forgotPasswordForCellphone(CellphoneParam("", ""))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updatePassword_success() = testScope.runTest {
        val responseBody = UpdatePasswordResponseBodyWithError(isSuccess = true)
        coEvery {
            service.updatePassword(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                oldPassword = any(),
                newPassword = any()
            )
        } returns Response.success(responseBody)

        val result = web.updatePassword("", true, "")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun updatePassword_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"手機號碼轉換錯誤"}}"""
        val responseBody =
            gson.fromJson(responseBodyJson, UpdatePasswordResponseBodyWithError::class.java)
        coEvery {
            service.updatePassword(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                oldPassword = any(),
                newPassword = any()
            )
        } returns Response.success(responseBody)

        val result = web.updatePassword("", true, "")
        result.getOrThrow()
    }

    @Test
    fun `getAccountInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = AccountInfoWithError(
            account = null,
            cellphone = null,
            contactEmail = null,
            fbMail = null,
            fbUrl = null,
            hasBindingAccount = null,
            hasUnverifiedContactEmail = null,
            registerTime = null
        )
        coEvery {
            service.getAccountInfo(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        web.getAccountInfo()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAccountInfo_success() = testScope.runTest {
        val responseBody = AccountInfoWithError(
            account = null,
            cellphone = null,
            contactEmail = null,
            fbMail = null,
            fbUrl = null,
            hasBindingAccount = null,
            hasUnverifiedContactEmail = null,
            registerTime = null
        )
        coEvery {
            service.getAccountInfo(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = web.getAccountInfo()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.account).isNull()
    }

    @Test(expected = ServerException::class)
    fun getAccountInfo_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"手機號碼轉換錯誤"}}"""
        val responseBody = gson.fromJson(responseBodyJson, AccountInfoWithError::class.java)
        coEvery {
            service.getAccountInfo(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = web.getAccountInfo()
        result.getOrThrow()
    }

    @Test
    fun `bindCellphone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = BindCellphoneResponseBodyWithError(
            verifyCodeDuration = 300,
            verifyCodeResendInterval = 120
        )
        coEvery {
            service.bindCellphone(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                countryCode = "886",
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)

        web.bindCellphone(
            CellphoneParam(
                countryCode = "886", cellphoneNumber = "1234"
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun bindCellphone_success() = testScope.runTest {
        val responseBody = BindCellphoneResponseBodyWithError(
            verifyCodeDuration = 300,
            verifyCodeResendInterval = 120
        )
        coEvery {
            service.bindCellphone(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                countryCode = "886",
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)

        val result = web.bindCellphone(
            CellphoneParam(
                countryCode = "886", cellphoneNumber = "1234"
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.verifyCodeDuration).isEqualTo(300)
        Truth.assertThat(data.verifyCodeResendInterval).isEqualTo(120)
    }

    @Test(expected = ServerException::class)
    fun bindCellphone_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"手機號碼轉換錯誤"}}"""
        val responseBody =
            gson.fromJson(responseBodyJson, BindCellphoneResponseBodyWithError::class.java)
        coEvery {
            service.bindCellphone(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                countryCode = any(),
                cellphoneNumber = any()
            )
        } returns Response.success(responseBody)

        val result = web.bindCellphone(
            CellphoneParam(
                countryCode = "886", cellphoneNumber = "1234"
            )
        )
        result.getOrThrow()
    }

    @Test
    fun `checkCellphoneBindingVerifyCode_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = CheckCellphoneBindingVerifyCodeResponseBodyWithError(isSuccess = true)
        coEvery {
            service.checkCellphoneBindingVerifyCode(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                countryCode = any(),
                cellphoneNumber = any(),
                verifyCode = any()
            )
        } returns Response.success(responseBody)

        web.checkCellphoneBindingVerifyCode(
            CellphoneParam(
                "886",
                "09123456789"
            ),
            "12345"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun checkCellphoneBindingVerifyCode_success() = testScope.runTest {
        val responseBody = CheckCellphoneBindingVerifyCodeResponseBodyWithError(isSuccess = true)
        coEvery {
            service.checkCellphoneBindingVerifyCode(
                url = any(),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                countryCode = any(),
                cellphoneNumber = any(),
                verifyCode = any()
            )
        } returns Response.success(responseBody)

        val result = web.checkCellphoneBindingVerifyCode(
            CellphoneParam(
                "886",
                "09123456789"
            ),
            "12345"
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun checkCellphoneBindingVerifyCode_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"新密碼不能為空"}}
        """.trimIndent()
        val responseBody = gson.fromJson(
            responseBodyJson,
            CheckCellphoneBindingVerifyCodeResponseBodyWithError::class.java
        )
        coEvery {
            service.checkCellphoneBindingVerifyCode(
                url = any(),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                countryCode = any(),
                cellphoneNumber = any(),
                verifyCode = any()
            )
        } returns Response.success(responseBody)

        val result = web.checkCellphoneBindingVerifyCode(
            CellphoneParam("", ""), ""
        )
        result.getOrThrow()
    }

    @Test
    fun `unbindCellphone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/LoginCheck/LoginCheck.ashx"
        val urlSlot = slot<String>()
        val responseBody = UnbindCellphoneResponseBodyWithError(isSuccess = true)
        coEvery {
            service.unbindCellphone(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        web.unbindCellphone()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun unbindCellphone_success() = testScope.runTest {
        val responseBody = UnbindCellphoneResponseBodyWithError(isSuccess = true)
        coEvery {
            service.unbindCellphone(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = web.unbindCellphone()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun unbindCellphone_failure_ServerException() = testScope.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"手機號碼轉換錯誤"}}"""
        val responseBody =
            gson.fromJson(responseBodyJson, UnbindCellphoneResponseBodyWithError::class.java)
        coEvery {
            service.unbindCellphone(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = web.unbindCellphone()
        result.getOrThrow()
    }
}