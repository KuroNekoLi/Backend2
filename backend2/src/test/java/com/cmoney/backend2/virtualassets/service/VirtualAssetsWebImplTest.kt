package com.cmoney.backend2.virtualassets.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.GetExchangeProductListResponseBody
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.ProductInfo
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeResponseBody
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
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class VirtualAssetsWebImplTest {

    private val testScope = TestScope()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: VirtualAssetsService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: VirtualAssetsWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web =
            VirtualAssetsWebImpl(
                manager = manager,
                gson = gson,
                service = service,
                TestDispatcherProvider()
            )
        coEvery {
            manager.getVirtualAssetsSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getExchangeProductList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}VirtualAssets/BonusExchange/Product/2"
        val urlSlot = slot<String>()
        val responseBody = GetExchangeProductListResponseBody(
            list = emptyList()
        )
        coEvery {
            manager.getAppId()
        } returns 2

        coEvery {
            service.getExchangeProductList(
                url = capture(urlSlot),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        web.getExchangeProductList()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getExchangeProductList_success() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetExchangeProductListResponseBody(
            list = listOf<ProductInfo>()
        )
        //設定api成功時的回傳
        coEvery {
            service.getExchangeProductList(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getExchangeProductList()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.list?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getExchangeProductList_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100103,
            "Message": "Error"
          }
        }""".trimIndent()
        //設定api成功時的回傳
        coEvery {
            service.getExchangeProductList(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = web.getExchangeProductList()
        checkServerException(result)
    }

    @Test
    fun `getGroupLastExchangeTime_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}VirtualAssets/BonusExchange/Product/MoreLastExchangeTime"
        val urlSlot = slot<String>()
        val responseBody = GetGroupLastExchangeTimeResponseBody(
            lastExchangeTime = emptyMap()
        )
        coEvery {
            service.getGroupLastExchangeTime(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getGroupLastExchangeTime(listOf(1L, 2L, 3L, 4L))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupLastExchangeTime_success() = testScope.runTest {
        //準備api成功時的回傳
        val map = HashMap<String, Long>()
        map["1"] = 123456L
        val responseBody = GetGroupLastExchangeTimeResponseBody(
            lastExchangeTime = map
        )
        //設定api成功時的回傳
        coEvery {
            service.getGroupLastExchangeTime(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getGroupLastExchangeTime(listOf(1L, 2L, 3L, 4L))
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.lastExchangeTime?.get("1")).isEqualTo(123456L)
    }

    @Test
    fun getGroupLastExchangeTime_failure() = testScope.runTest {
        val json = ""

        //設定api成功時的回傳
        coEvery {
            service.getGroupLastExchangeTime(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = web.getGroupLastExchangeTime(listOf(1L, 2L, 3L, 4L))
        checkHttpException(result)
    }

    @Test
    fun `exchange_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}VirtualAssets/BonusExchange/Product/Exchange/1"
        val urlSlot = slot<String>()
        coEvery {
            service.exchange(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        web.exchange(1L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun exchange_success() = testScope.runTest {
        coEvery {
            service.exchange(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.exchange(1L)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test
    fun exchange_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100103,
            "Message": "期限內兌換過不能再換"
          }
        }""".trimIndent()

        coEvery {
            service.exchange(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, json.toResponseBody())

        val result = web.exchange(1L)
        checkServerException(result)
    }


    private fun <T> checkServerException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    private fun <T> checkHttpException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as HttpException
        Truth.assertThat(exception).isNotNull()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}