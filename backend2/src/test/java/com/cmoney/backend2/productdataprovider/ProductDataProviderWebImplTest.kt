package com.cmoney.backend2.productdataprovider

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderService
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWeb
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWebImpl
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
    fun `getProductByGraphQL_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ProductDataProvider/Product/GraphQLQuery"
        val urlSlot = slot<String>()
        coEvery {
            service.getProductByGraphQL(
                url = capture(urlSlot),
                authorization = any(),
                query = any()
            )
        } returns Response.success(
            "{}".toResponseBody()
        )
        web.getProductBySalesId(1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getProductBySalesId_success() = testScope.runTest {
        coEvery {
            service.getProductByGraphQL(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.success(
            // language=JSON
            """{
                "data": {
                    "saleInfo": {
                        "name": "30天",
                        "price": 299.0000,
                        "itemPrice": 999.0000,
                        "productId": 4399,
                        "description": null,
                        "productInformation": {
                            "name": "鄉民PTT「金庸」投資專欄測試",
                            "shortDesc": "鄉民PTT金庸分享看盤經驗，在股海江湖中帶你拆解招式，突破主力迷思，首要戰勝心魔方能笑看紅塵。專欄內容純屬個人操作與看法，不做任何買賣建議，僅供參考，盈利自負",
                            "authorInfoSet": [
                                {
                                    "authorName": "台股籌碼戰-金庸",
                                    "memberId": 123590,
                                    "account": "alvin_hsu@cmoney.com.tw"
                                }
                            ]
                        }
                    }
                }
            }""".trimIndent().toResponseBody()
        )
        val result = web.getProductBySalesId(1)
        Truth.assertThat(result.getOrNull()?.productId).isEqualTo(4399L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getProductBySalesId_failure() = testScope.runTest {
        coEvery {
            service.getProductByGraphQL(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = web.getProductBySalesId(1)
        Truth.assertThat(result.getOrNull()?.productId).isEqualTo(null)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getSalesItemBySubjectId_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ProductDataProvider/Product/GraphQLQuery"
        val urlSlot = slot<String>()
        coEvery {
            service.getProductByGraphQL(
                url = capture(urlSlot),
                authorization = any(),
                query = any()
            )
        } returns Response.success(
            "{}".toResponseBody()
        )
        web.getSalesItemBySubjectId(1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSalesItemBySubjectId_success() = testScope.runTest {
        coEvery {
            service.getProductByGraphQL(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.success(
            // language=JSON
            """
            {
            	"data": {
            		"productInfoSet": [
            			{
            				"id": 4399,
            				"name": "鄉民PTT「金庸」投資專欄測試",
            				"status": 1,
            				"logoPath": "C3D09441.png",
            				"saleInfoSet": [
            					{
            						"id": 6497,
            						"name": "30天",
            						"isShow": true
            					}
            				]
            			},
            			{
            				"id": 4403,
            				"name": "Ann-test",
            				"status": 1,
            				"logoPath": "C9C7BEBB-A48F-4089-A113-7DDF6D531549.png",
            				"saleInfoSet": [
            					{
            						"id": 6502,
            						"name": "一個月",
            						"isShow": false
            					}
            				]
            			}
            		]
            	}
            }
        """.trimIndent().toResponseBody()
        )
        val result = web.getSalesItemBySubjectId(1)
        Truth.assertThat(result.getOrNull()?.firstOrNull()?.productId).isEqualTo(4399)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getSalesItemBySubjectId_failure() = testScope.runTest {
        coEvery {
            service.getProductByGraphQL(
                url = any(),
                authorization = any(),
                query = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = web.getSalesItemBySubjectId(1)
        Truth.assertThat(result.getOrNull()?.firstOrNull()?.productId).isEqualTo(null)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}
