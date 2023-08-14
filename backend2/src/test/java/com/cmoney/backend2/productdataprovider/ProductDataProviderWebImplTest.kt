package com.cmoney.backend2.productdataprovider

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderService
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWeb
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWebImpl
import com.cmoney.backend2.productdataprovider.service.api.getproductbysalesid.GetProductBySalesIdResponseBody
import com.cmoney.backend2.productdataprovider.service.api.getsalesitembysubjectid.GetSalesItemBySubjectIdResponseBody
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
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class ProductDataProviderWebImplTest {
    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: ProductDataProviderService
    private lateinit var web: ProductDataProviderWeb
    private val gson = GsonBuilder().serializeNulls().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = ProductDataProviderWebImpl(
            manager = manager,
            gson = gson,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getProductDataProviderSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getProductBySalesId_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ProductDataProvider/Product/GraphQLQuery"
        val urlSlot = slot<String>()
        val responseBody = GetProductBySalesIdResponseBody(data = null)
        coEvery {
            service.getProductBySalesId(
                url = capture(urlSlot),
                authorization = any(),
                query = any()
            )
        } returns Response.success(responseBody)
        web.getProductBySalesId(1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getProductBySalesId_success() = testScope.runTest {
        val responseJsonString = """
                {
                  "data": {
                    "saleInfo": {
                      "name": "主力大_7天",
                      "price": 0.0,
                      "itemPrice": 13500.0,
                      "productId": 3696,
                      "productInformation": {
                        "name": "【贈品】籌碼K線專業版",
                        "shortDesc": "",
                        "authorInfoSet": [
                          {
                            "authorName": "苦命程序員",
                            "memberId": 15511,
                            "account": "davidlin@cmoney.com.tw"
                          }
                        ]
                      }
                    }
                  }
                }
            """.trimIndent()
        val responseBody =
            gson.fromJson(responseJsonString, GetProductBySalesIdResponseBody::class.java)
        coEvery {
            service.getProductBySalesId(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.success(
            responseBody
        )
        val result = web.getProductBySalesId(3696)
            .getOrThrow()
        Truth.assertThat(result.name).isEqualTo("主力大_7天")
        Truth.assertThat(result.price).isEqualTo(0.0)
        Truth.assertThat(result.originalPrice).isEqualTo(13500.0)
        Truth.assertThat(result.productId).isEqualTo(3696)
        Truth.assertThat(result.authorName).isEqualTo("苦命程序員")
        Truth.assertThat(result.displayName).isEqualTo("【贈品】籌碼K線專業版")
        Truth.assertThat(result.displayDesc).isEqualTo("")
    }

    @Test
    fun getProductBySalesId_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getProductBySalesId(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.getProductBySalesId(1)
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isInstanceOf(ServerException::class.java)
    }

    @Test
    fun `getProductBySalesId_success_IllegalArgumentException 找不到符合的商品`() = testScope.runTest {
        val responseJsonString = """
                {"data":{"saleInfo":null}}
            """.trimIndent()
        val responseBody =
            gson.fromJson(responseJsonString, GetProductBySalesIdResponseBody::class.java)
        coEvery {
            service.getProductBySalesId(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.success(responseBody)
        val result = web.getProductBySalesId(1)
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("找不到符合的商品")
    }

    @Test
    fun `getSalesItemBySubjectId_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ProductDataProvider/Product/GraphQLQuery"
        val urlSlot = slot<String>()
        val responseBody = GetSalesItemBySubjectIdResponseBody(data = null)
        coEvery {
            service.getSalesItemBySubjectId(
                url = capture(urlSlot),
                authorization = any(),
                query = any()
            )
        } returns Response.success(responseBody)
        web.getSalesItemBySubjectId(1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSalesItemBySubjectId_success() = testScope.runTest {
        val responseJsonString = """
            {
              "data": {
                "productInfoSet": [
                  {
                    "id": 5038,
                    "name": "String Yeh｜投資專欄",
                    "status": 1,
                    "logoPath": "String Yeh-修.jpg",
                    "saleInfoSet": [
                      {
                        "id": 5821,
                        "name": "月訂閱(30天)",
                        "isShow": true,
                        "rank": 0
                      },
                      {
                        "id": 5834,
                        "name": "年訂閱(365天)",
                        "isShow": true,
                        "rank": 1
                      }
                    ]
                  }
                ]
              }
            }
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseJsonString, GetSalesItemBySubjectIdResponseBody::class.java)
        coEvery {
            service.getSalesItemBySubjectId(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.success(responseBody)
        val result = web.getSalesItemBySubjectId(1)
            .getOrThrow()
        Truth.assertThat(result[0].productId).isEqualTo(5038)
        Truth.assertThat(result[0].productName).isEqualTo("String Yeh｜投資專欄")
        Truth.assertThat(result[0].saleId).isEqualTo(5821)
        Truth.assertThat(result[0].title).isEqualTo("月訂閱(30天)")
        Truth.assertThat(result[0].isShown).isTrue()
        Truth.assertThat(result[1].productId).isEqualTo(5038)
        Truth.assertThat(result[1].productName).isEqualTo("String Yeh｜投資專欄")
        Truth.assertThat(result[1].saleId).isEqualTo(5834)
        Truth.assertThat(result[1].title).isEqualTo("年訂閱(365天)")
        Truth.assertThat(result[1].isShown).isTrue()
    }

    @Test
    fun `getSalesItemBySubjectId_item is empty_success`() = testScope.runTest {
        val responseJsonString = """
           {"data":{"productInfoSet":[]}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseJsonString, GetSalesItemBySubjectIdResponseBody::class.java)
        coEvery {
            service.getSalesItemBySubjectId(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.success(responseBody)
        val result = web.getSalesItemBySubjectId(1)
            .getOrThrow()
        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun getSalesItemBySubjectId_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getSalesItemBySubjectId(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.getSalesItemBySubjectId(1)
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isInstanceOf(ServerException::class.java)
    }


    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}
