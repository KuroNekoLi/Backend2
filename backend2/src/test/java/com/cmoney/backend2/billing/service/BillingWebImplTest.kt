package com.cmoney.backend2.billing.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.base.model.calladapter.RecordApiLogCallAdapterFactory
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.billing.MainCoroutineRule
import com.cmoney.backend2.billing.TestApplication
import com.cmoney.backend2.billing.TestDispatcher
import com.cmoney.backend2.billing.TestSetting
import com.cmoney.backend2.billing.service.api.getappauth.GetAppAuthResponseBody
import com.cmoney.backend2.billing.service.api.getauth.GetAuthResponseBody
import com.cmoney.backend2.billing.service.api.getdevelpoerpayload.GetDeveloperPayloadResponseBody
import com.cmoney.backend2.billing.service.api.getproductinfo.ProductInformation
import com.cmoney.backend2.billing.service.api.isPurchasable.IsPurchasableResponseBody
import com.cmoney.backend2.billing.service.common.*
import com.cmoney.backend2.testing.noContentMockResponse
import com.cmoney.backend2.testing.runBlockingWithCheckLog
import com.cmoney.backend2.testing.toBadRequestMockResponse
import com.cmoney.backend2.testing.toMockResponse
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class BillingWebImplTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val server = MockWebServer()

    private lateinit var billingWeb: BillingWeb
    private lateinit var setting: Setting
    @RelaxedMockK
    private lateinit var logDataRecorder: LogDataRecorder
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    companion object {
        private const val HAUWEI_PRODUCT_INFO = "huawei_product_info.json"
        private const val GOOGLE_PRODUCT_INFO = "google_product_info.json"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        val service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RecordApiLogCallAdapterFactory(setting) { logDataRecorder })
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BillingService::class.java)
        billingWeb = BillingWebImpl(
            service = service,
            gson = gson,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `getDeveloperPayload_成功_有定義的資料`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetDeveloperPayloadResponseBody(
            id = 14948
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getDeveloperPayload()
        assertThat(result.isSuccess).isTrue()
        val id = result.getOrThrow()
        assertThat(id).isEqualTo(14948)
    }

    @Test
    fun `getDeveloperPayload_失敗400_有Error物件`() = runBlockingWithCheckLog(logDataRecorder) {
        val errorBody = CMoneyError(
            CMoneyError.Detail(
                code = 10001
            )
        )
        server.enqueue(errorBody.toBadRequestMockResponse(gson))

        val result = billingWeb.getDeveloperPayload()
        checkServerException(result, 10001)
    }

    @Test
    fun `isReadyToPurchase_還沒開放購買_false`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = IsPurchasableResponseBody(
            isPurchasable = false
        )
        server.enqueue(responseBody.toMockResponse(gson))
        val result = billingWeb.isReadyToPurchase()
        assertThat(result.isSuccess).isTrue()
        val isReady = result.getOrThrow()
        assertThat(isReady).isFalse()
    }


    @Test
    fun `isReadyToPurchase_開放購買_true`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = IsPurchasableResponseBody(
            isPurchasable = true
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.isReadyToPurchase()
        assertThat(result.isSuccess).isTrue()
        val isReady = result.getOrThrow()
        assertThat(isReady).isTrue()
    }

    @Test
    fun `isReadyToPurchase_錯誤_ServerException`() = runBlockingWithCheckLog(logDataRecorder) {
        val errorBody = CMoneyError(
            CMoneyError.Detail(
                code = 10001
            )
        )
        server.enqueue(errorBody.toBadRequestMockResponse(gson))

        val result = billingWeb.isReadyToPurchase()
        assertThat(result.isSuccess).isFalse()
        checkServerException(result, 10001)
    }

    @Test
    fun `getProductInfo_華為商品成功_是空的清單`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(emptyList<ProductInformation>().toMockResponse(gson))

        val result = billingWeb.getProductInfo()
        assertThat(result.isSuccess).isTrue()
        val productList = result.getOrThrow()
        assertThat(productList).isEmpty()
    }

    @Test
    fun `getProductInfo_成功_不是空的清單`() = runBlockingWithCheckLog(logDataRecorder) {
        val productInfoJson = context.assets.open(HAUWEI_PRODUCT_INFO)
            .bufferedReader()
            .use {
                it.readText()
            }
        val responseBody = gson.fromJson<List<ProductInformation>>(
            productInfoJson,
            object : TypeToken<List<ProductInformation>>() {}.type
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getProductInfo()
        assertThat(result.isSuccess).isTrue()
        val productList = result.getOrThrow()
        assertThat(productList).isNotEmpty()
    }

    @Test
    fun `getIapProductInfo_失敗_有錯誤碼`() = runBlockingWithCheckLog(logDataRecorder) {
        val errorBody = CMoneyError(
            CMoneyError.Detail(
                code = 10001
            )
        )
        server.enqueue(errorBody.toBadRequestMockResponse(gson))

        val result = billingWeb.getProductInfo()
        checkServerException(result, 10001)
    }

    @Test
    fun `getAuthStatus_authType是0_授權是免費`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getAuthStatus_authType是1_授權是手機`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAuthResponseBody(
            authType = 1,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Phone)
    }

    @Test
    fun `getAuthStatus_authType是2_授權是PC`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAuthResponseBody(
            authType = 2,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }

    @Test
    fun `getAuthStatus_authType是3_授權是PC`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAuthResponseBody(
            authType = 3,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }


    @Test
    fun `getAuthStatus_authType是其他_授權是免費`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAuthResponseBody(
            authType = 4,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getAuthStatus_授權日期期格式正確`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getAuthStatus()
        val data = result.getOrThrow()
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val expect = simpleDateFormat.parse(responseBody.authExpTime.orEmpty())
        assertThat(data.expireTime).isEqualTo(expect)
    }

    @Test(expected = ParseException::class)
    fun `getAuthStatus_授權日期期格式錯誤_ParseException`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAuthResponseBody(
            authType = 0,
            authExpTime = "2020",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getAuthStatus()
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getTargetAppAuthStatus_authType是0_授權是免費`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAppAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是1_授權是手機`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAppAuthResponseBody(
            authType = 1,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Phone)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是2_授權是PC`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAppAuthResponseBody(
            authType = 2,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是3_授權是PC`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAppAuthResponseBody(
            authType = 3,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Pc)
    }

    @Test
    fun `getTargetAppAuthStatus_authType是其他_授權是免費`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAppAuthResponseBody(
            authType = 4,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        assertThat(data.state).isEqualTo(Authorization.State.Free)
    }

    @Test
    fun `getTargetAppAuthStatus_授權日期期格式正確`() = runBlockingWithCheckLog(logDataRecorder) {
        val responseBody = GetAppAuthResponseBody(
            authType = 0,
            authExpTime = "2020/05/05",
            responseCode = 1,
            responseMsg = ""
        )
        server.enqueue(responseBody.toMockResponse(gson))

        val result = billingWeb.getTargetAppAuthStatus(
            queryAppId = 1
        )
        val data = result.getOrThrow()
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val expect = simpleDateFormat.parse(responseBody.authExpTime.orEmpty())
        Truth.assertThat(data.expireTime).isEqualTo(expect)
    }

    @Test(expected = ParseException::class)
    fun `getTargetAppAuthStatus_授權日期期格式錯誤_ParseException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val responseBody = GetAppAuthResponseBody(
                authType = 0,
                authExpTime = "2020",
                responseCode = 1,
                responseMsg = ""
            )
            server.enqueue(responseBody.toMockResponse(gson))

            val result = billingWeb.getTargetAppAuthStatus(
                queryAppId = 1
            )
            assertThat(result.isSuccess).isFalse()
            result.getOrThrow()
        }

    @Test
    fun `verifyHuaweiInAppReceipt_購買成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())
        val result = billingWeb.verifyHuaweiInAppReceipt(
            receipt = InAppHuaweiReceipt(
                accountFlag = 0,
                purchaseToken = "",
                productId = "",
                receiptJson = "",
                signature = ""
            )
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyHuaweiInAppReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

            val result = billingWeb.verifyHuaweiInAppReceipt(
                receipt = InAppHuaweiReceipt(
                    accountFlag = 0,
                    purchaseToken = "",
                    productId = "",
                    receiptJson = "",
                    signature = ""
                )
            )
            checkServerException(result, 10001)
        }

    @Test
    fun `verifyHuaweiSubReceipt_購買成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyHuaweiSubReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

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
            checkServerException(result, 10001)
        }

    @Test
    fun `recoveryHuaweiInAppReceipt_成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())
        val result = billingWeb.recoveryHuaweiInAppReceipt(
            receipts = emptyList()
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }


    @Test
    fun `recoveryHuaweiInAppReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

            val result = billingWeb.recoveryHuaweiInAppReceipt(
                receipts = emptyList()
            )
            checkServerException(result, 10001)
        }

    @Test
    fun `recoveryHuaweiSubReceipt_成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())
        val result = billingWeb.recoveryHuaweiSubReceipt(
            receipts = emptyList()
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `recoveryHuaweiSubReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

            val result = billingWeb.recoveryHuaweiSubReceipt(
                receipts = emptyList()
            )
            checkServerException(result, 10001)
        }

    @Test
    fun `verifyGoogleInAppReceipt_購買成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())
        val result = billingWeb.verifyGoogleInAppReceipt(
            receipt = InAppGoogleReceipt(
                purchaseToken = "",
                productId = ""
            )
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyGoogleInAppReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

            val result = billingWeb.verifyGoogleInAppReceipt(
                receipt = InAppGoogleReceipt(
                    purchaseToken = "",
                    productId = ""
                )
            )
            checkServerException(result, 10001)
        }

    @Test
    fun `verifyGoogleSubReceipt_購買成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())

        val result = billingWeb.verifyGoogleSubReceipt(
            receipt = SubGoogleReceipt(
                purchaseToken = "",
                productId = ""
            )
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `verifyGoogleSubReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

            val result = billingWeb.verifyGoogleSubReceipt(
                receipt = SubGoogleReceipt(
                    purchaseToken = "",
                    productId = ""
                )
            )
            checkServerException(result, 10001)
        }

    @Test
    fun `recoveryGoogleInAppReceipt_成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())

        val result = billingWeb.recoveryGoogleInAppReceipt(
            receipts = emptyList()
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }


    @Test
    fun `recoveryGoogleInAppReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

            val result = billingWeb.recoveryGoogleInAppReceipt(
                receipts = emptyList()
            )
            checkServerException(result, 10001)
        }

    @Test
    fun `recoveryGoogleSubReceipt_成功`() = runBlockingWithCheckLog(logDataRecorder) {
        server.enqueue(noContentMockResponse())

        val result = billingWeb.recoveryGoogleSubReceipt(
            receipts = emptyList()
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun `recoveryGoogleSubReceipt_購買失敗，收據錯誤_ServerException`() =
        runBlockingWithCheckLog(logDataRecorder) {
            val errorBody = CMoneyError(
                CMoneyError.Detail(
                    code = 10001
                )
            )
            server.enqueue(errorBody.toBadRequestMockResponse(gson))

            val result = billingWeb.recoveryGoogleSubReceipt(
                receipts = emptyList()
            )
            checkServerException(result, 10001)
        }

    private fun <T> checkServerException(result: Result<T>, errorCode: Int) {
        assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        assertThat(exception).isNotNull()
        assertThat(exception.code).isEqualTo(errorCode)
    }
}