package com.cmoney.backend2.billing.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.billing.TestApplication
import com.cmoney.backend2.billing.service.api.authbycmoney.AuthByCMoneyResponseBody
import com.cmoney.backend2.billing.service.api.getappauth.GetAppAuthResponseBody
import com.cmoney.backend2.billing.service.api.getauth.GetAuthResponseBody
import com.cmoney.backend2.billing.service.api.getdevelpoerpayload.GetDeveloperPayloadResponseBody
import com.cmoney.backend2.billing.service.api.getproductinfo.ProductInformation
import com.cmoney.backend2.billing.service.api.isPurchasable.IsPurchasableResponseBody
import com.cmoney.backend2.billing.service.common.*
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class BillingWebImplTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)
    @MockK
    lateinit var service: BillingService
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager
    private lateinit var billingWeb: BillingWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    companion object {
        private const val HAUWEI_PRODUCT_INFO = "huawei_product_info.json"
        private const val GOOGLE_PRODUCT_INFO = "google_product_info.json"

    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        billingWeb = BillingWebImpl(
            service = service,
            gson = gson,
            manager = manager,
            dispatcher = TestDispatcherProvider()
        )
    }

    @Test
    fun `getDeveloperPayload_成功_有定義的資料`() = testScope.runTest {
        val responseBody = GetDeveloperPayloadResponseBody(
            id = 14948
        )
        coEvery {
            service.getDeveloperPayload(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = billingWeb.getDeveloperPayload()
        assertThat(result.isSuccess).isTrue()
        val id = result.getOrThrow()
        assertThat(id).isEqualTo(14948)
    }

    @Test
    fun `getDeveloperPayload_失敗400_有Error物件`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getDeveloperPayload(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)
        val result = billingWeb.getDeveloperPayload()
        checkServerException(result, 10001)
    }

    @Test
    fun `isReadyToPurchase_還沒開放購買_false`() = testScope.runTest {
        val responseBody = IsPurchasableResponseBody(
            isPurchasable = false
        )
        coEvery {
            service.isReadyToPurchase(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = billingWeb.isReadyToPurchase()
        assertThat(result.isSuccess).isTrue()
        val isReady = result.getOrThrow()
        assertThat(isReady).isFalse()
    }


    @Test
    fun `isReadyToPurchase_開放購買_true`() = testScope.runTest {
        val responseBody = IsPurchasableResponseBody(
            isPurchasable = true
        )
        coEvery {
            service.isReadyToPurchase(
                authorization = any(),
                url = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.isReadyToPurchase()
        assertThat(result.isSuccess).isTrue()
        val isReady = result.getOrThrow()
        assertThat(isReady).isTrue()
    }

    @Test
    fun `isReadyToPurchase_錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.isReadyToPurchase(
                authorization = any(),
                url = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.isReadyToPurchase()
        assertThat(result.isSuccess).isFalse()
        checkServerException(result, 10001)
    }

    @Test
    fun `getProductInfo_華為商品成功_是空的清單`() = testScope.runTest {
        coEvery {
            service.getIapProductInformation(
                authorization = any(),
                requestBody = any(),
                url = any(),
            )
        } returns Response.success<List<ProductInformation>>(emptyList())
        val result = billingWeb.getProductInfo()
        assertThat(result.isSuccess).isTrue()
        val productList = result.getOrThrow()
        assertThat(productList).isEmpty()
    }

    @Test
    fun `getProductInfo_成功_不是空的清單`() = testScope.runTest {
        val productInfoJson = context.assets.open(HAUWEI_PRODUCT_INFO)
            .bufferedReader()
            .use {
                it.readText()
            }
        val responseBody = gson.fromJson<List<ProductInformation>>(
            productInfoJson,
            object : TypeToken<List<ProductInformation>>() {}.type
        )
        coEvery {
            service.getIapProductInformation(
                authorization = any(),
                requestBody = any(),
                url = any(),
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getProductInfo()
        assertThat(result.isSuccess).isTrue()
        val productList = result.getOrThrow()
        assertThat(productList).isNotEmpty()
    }

    @Test
    fun `getIapProductInfo_失敗_有錯誤碼`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIapProductInformation(
                authorization = any(),
                requestBody = any(),
                url = any(),
            )
        } returns Response.error<List<ProductInformation>>(400, errorBody)

        val result = billingWeb.getProductInfo()
        checkServerException(result, 10001)
    }

    @Test
    fun `getAuthStatus_authType是0_授權是免費`() = testScope.runTest {
        val responseBody = GetAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getAuthStatus(
                authorization = any(),
                appId = any(),
                guid = any(),
                url = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getAuthStatus_authType是1_授權是手機`() = testScope.runTest {
        val responseBody = GetAuthResponseBody(
            authType = 1,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getAuthStatus(
                authorization = any(),
                appId = any(),
                guid = any(),
                url = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Phone)
    }

    @Test
    fun `getAuthStatus_authType是2_授權是PC`() = testScope.runTest {
        val responseBody = GetAuthResponseBody(
            authType = 2,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getAuthStatus(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }

    @Test
    fun `getAuthStatus_authType是3_授權是PC`() = testScope.runTest {
        val responseBody = GetAuthResponseBody(
            authType = 3,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getAuthStatus(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }


    @Test
    fun `getAuthStatus_authType是其他_授權是免費`() = testScope.runTest {
        val responseBody = GetAuthResponseBody(
            authType = 4,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getAuthStatus(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getAuthStatus_授權日期期格式正確`() = testScope.runTest {
        val responseBody = GetAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getAuthStatus(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getAuthStatus()
        val data = result.getOrThrow()
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val expect = simpleDateFormat.parse(responseBody.authExpTime.orEmpty())
        assertThat(data.expireTime).isEqualTo(expect)
    }

    @Test(expected = ParseException::class)
    fun `getAuthStatus_授權日期期格式錯誤_ParseException`() = testScope.runTest {
        val responseBody = GetAuthResponseBody(
            authType = 0,
            authExpTime = "2020",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getAuthStatus(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getTargetAppAuthStatus_authType是0_授權是免費`() = testScope.runTest {
        val responseBody = GetAppAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getTargetAppAuthStatus(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                queryAppId = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是1_授權是手機`() = testScope.runTest {
        val responseBody = GetAppAuthResponseBody(
            authType = 1,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getTargetAppAuthStatus(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                queryAppId = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Phone)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是2_授權是PC`() = testScope.runTest {
        val responseBody = GetAppAuthResponseBody(
            authType = 2,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getTargetAppAuthStatus(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                queryAppId = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是3_授權是PC`() = testScope.runTest {
        val responseBody = GetAppAuthResponseBody(
            authType = 3,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getTargetAppAuthStatus(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                queryAppId = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是其他_授權是免費`() = testScope.runTest {
        val responseBody = GetAppAuthResponseBody(
            authType = 4,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getTargetAppAuthStatus(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                queryAppId = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getTargetAppAuthStatus_授權日期期格式正確`() = testScope.runTest {
        val responseBody = GetAppAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getTargetAppAuthStatus(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                queryAppId = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        val data = result.getOrThrow()
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val expect = simpleDateFormat.parse(responseBody.authExpTime.orEmpty())
        Truth.assertThat(data.expireTime).isEqualTo(expect)
    }

    @Test(expected = ParseException::class)
    fun `getTargetAppAuthStatus_授權日期期格式錯誤_ParseException`() = testScope.runTest {
        val responseBody = GetAppAuthResponseBody(
            authType = 0,
            authExpTime = "2020",
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getTargetAppAuthStatus(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                queryAppId = any()
            )
        } returns Response.success(responseBody)

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `verifyHuaweiInAppReceipt_購買成功`() = testScope.runTest {
        coEvery {
            service.verifyHuaweiInAppReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.verifyHuaweiInAppReceipt(
            receipt = InAppHuaweiReceipt(
                accountFlag = 0,
                purchaseToken = "",
                productId = "",
                receiptJson = "",
                signature = ""
            )
        )
        coVerify { service.verifyHuaweiInAppReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyHuaweiInAppReceipt_購買失敗，收據錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.verifyHuaweiInAppReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.verifyHuaweiInAppReceipt(
            receipt = InAppHuaweiReceipt(
                accountFlag = 0,
                purchaseToken = "",
                productId = "",
                receiptJson = "",
                signature = ""
            )
        )
        coVerify { service.verifyHuaweiInAppReceipt(any(), any(), any()) }
        checkServerException(result, 10001)
    }

    @Test
    fun `verifyHuaweiSubReceipt_購買成功`() = testScope.runTest {
        coEvery {
            service.verifyHuaweiSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.verifyHuaweiSubReceipt(
            receipt = SubHuaweiReceipt(
                accountFlag = 0,
                purchaseToken = "",
                productId = "",
                subscriptionId = "",
                receiptJson = "",
                signature = ""
            )
        )
        coVerify { service.verifyHuaweiSubReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyHuaweiSubReceipt_購買失敗，收據錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.verifyHuaweiSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.verifyHuaweiSubReceipt(
            receipt = SubHuaweiReceipt(
                accountFlag = 0,
                purchaseToken = "",
                productId = "",
                subscriptionId = "",
                receiptJson = "",
                signature = ""
            )
        )
        coVerify { service.verifyHuaweiSubReceipt(any(), any(), any()) }
        checkServerException(result, 10001)
    }

    @Test
    fun `recoveryHuaweiInAppReceipt_成功`() = testScope.runTest {
        coEvery {
            service.recoveryHuaweiInAppReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.recoveryHuaweiInAppReceipt(
            receipts = emptyList()
        )
        coVerify { service.recoveryHuaweiInAppReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }


    @Test
    fun `recoveryHuaweiInAppReceipt_購買失敗，收據錯誤_ServerException`() =
        testScope.runTest {
            val errorBody = gson.toJson(
                CMoneyError(
                    detail = CMoneyError.Detail(
                        code = 10001
                    )
                )
            ).toResponseBody()
            coEvery {
                service.recoveryHuaweiInAppReceipt(
                    url = any(),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.error(400, errorBody)

            val result = billingWeb.recoveryHuaweiInAppReceipt(
                receipts = emptyList()
            )
            coVerify { service.recoveryHuaweiInAppReceipt(any(), any(), any()) }
            checkServerException(result, 10001)
        }

    @Test
    fun `recoveryHuaweiSubReceipt_成功`() = testScope.runTest {
        coEvery {
            service.recoveryHuaweiSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.recoveryHuaweiSubReceipt(
            receipts = emptyList()
        )
        coVerify { service.recoveryHuaweiSubReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `recoveryHuaweiSubReceipt_購買失敗，收據錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.recoveryHuaweiSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.recoveryHuaweiSubReceipt(
            receipts = emptyList()
        )
        coVerify { service.recoveryHuaweiSubReceipt(any(), any(), any()) }
        checkServerException(result, 10001)
    }

    @Test
    fun `verifyGoogleInAppReceipt_購買成功`() = testScope.runTest {
        coEvery {
            service.verifyGoogleInAppReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.verifyGoogleInAppReceipt(
            receipt = InAppGoogleReceipt(
                purchaseToken = "",
                productId = ""
            )
        )
        coVerify { service.verifyGoogleInAppReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyGoogleInAppReceipt_購買失敗，收據錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.verifyGoogleInAppReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.verifyGoogleInAppReceipt(
            receipt = InAppGoogleReceipt(
                purchaseToken = "",
                productId = ""
            )
        )
        coVerify { service.verifyGoogleInAppReceipt(any(), any(), any()) }
        checkServerException(result, 10001)
    }

    @Test
    fun `verifyGoogleSubReceipt_購買成功`() = testScope.runTest {
        coEvery {
            service.verifyGoogleSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.verifyGoogleSubReceipt(
            receipt = SubGoogleReceipt(
                purchaseToken = "",
                productId = ""
            )
        )
        coVerify { service.verifyGoogleSubReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyGoogleSubReceipt_購買失敗，收據錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.verifyGoogleSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.verifyGoogleSubReceipt(
            receipt = SubGoogleReceipt(
                purchaseToken = "",
                productId = ""
            )
        )
        coVerify { service.verifyGoogleSubReceipt(any(), any(), any()) }
        checkServerException(result, 10001)
    }

    @Test
    fun `recoveryGoogleInAppReceipt_成功`() = testScope.runTest {
        coEvery {
            service.recoveryGoogleInAppReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.recoveryGoogleInAppReceipt(
            receipts = emptyList()
        )
        coVerify { service.recoveryGoogleInAppReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }


    @Test
    fun `recoveryGoogleInAppReceipt_購買失敗，收據錯誤_ServerException`() =
        testScope.runTest {
            val errorBody = gson.toJson(
                CMoneyError(
                    detail = CMoneyError.Detail(
                        code = 10001
                    )
                )
            ).toResponseBody()
            coEvery {
                service.recoveryGoogleInAppReceipt(
                    url = any(),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.error(400, errorBody)

            val result = billingWeb.recoveryGoogleInAppReceipt(
                receipts = emptyList()
            )
            coVerify { service.recoveryGoogleInAppReceipt(any(), any(), any()) }
            checkServerException(result, 10001)
        }

    @Test
    fun `recoveryGoogleSubReceipt_成功`() = testScope.runTest {
        coEvery {
            service.recoveryGoogleSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = billingWeb.recoveryGoogleSubReceipt(
            receipts = emptyList()
        )
        coVerify { service.recoveryGoogleSubReceipt(any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }


    @Test
    fun `recoveryGoogleSubReceipt_購買失敗，收據錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.recoveryGoogleSubReceipt(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.recoveryGoogleSubReceipt(
            receipts = emptyList()
        )
        coVerify { service.recoveryGoogleSubReceipt(any(), any(), any()) }
        checkServerException(result, 10001)
    }

    @Test
    fun `getAuthByCMoney_成功`() = testScope.runTest {
        val responseBody = AuthByCMoneyResponseBody(
            isAuth = false
        )
        coEvery {
            service.getAuthByCMoney(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result = billingWeb.getAuthByCMoney(2)
        coVerify { service.getAuthByCMoney(any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(false)
    }

    @Test
    fun `getAuthByCMoney_錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getAuthByCMoney(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.getAuthByCMoney(2)
        assertThat(result.isSuccess).isFalse()
        checkServerException(result, 10001)
    }

    @Test
    fun `getHistoryCount_成功`() = testScope.runTest {
        val functionIds: Long = 6531
        val responseBody = "{\"$functionIds\":555}".toResponseBody()
        coEvery {
            service.getHistoryCount(
                url = any(),
                authorization = any(),
                productType = any(),
                functionIds = any()
            )
        } returns Response.success(responseBody)
        val result = billingWeb.getHistoryCount(888003, functionIds)
        coVerify { service.getHistoryCount(any(), any(), any(), any()) }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(555)
    }

    @Test
    fun `getHistoryCount_錯誤_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getHistoryCount(
                url = any(),
                authorization = any(),
                productType = any(),
                functionIds = any()
            )
        } returns Response.error(400, errorBody)

        val result = billingWeb.getHistoryCount(888003, 666)
        assertThat(result.isSuccess).isFalse()
        checkServerException(result, 10001)
    }

    private fun <T> checkServerException(result: Result<T>, errorCode: Int) {
        assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        assertThat(exception).isNotNull()
        assertThat(exception.code).isEqualTo(errorCode)
    }
}