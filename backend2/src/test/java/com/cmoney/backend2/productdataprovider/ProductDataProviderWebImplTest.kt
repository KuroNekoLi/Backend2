package com.cmoney.backend2.productdataprovider

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderService
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWeb
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWebImpl
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: ProductDataProviderService
    private lateinit var web: ProductDataProviderWeb
    private val gson = GsonBuilder().serializeNulls().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = ProductDataProviderWebImpl(
            gson = gson,
            service = service,
            setting = TestSetting(),
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun getProductBySalesId_success() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getProductByGraphQL(any(), any())
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
}""".toResponseBody())
        val result = web.getProductBySalesId(1)
        Truth.assertThat(result.getOrNull()?.productId).isEqualTo(4399L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getProductBySalesId_failure() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getProductByGraphQL(any(), any())
        } returns Response.error(400, "".toResponseBody())
        val result = web.getProductBySalesId(1)
        Truth.assertThat(result.getOrNull()?.productId).isEqualTo(null)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getSalesItemBySubjectId_success() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getProductByGraphQL(any(), any())
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
        """.trimIndent().toResponseBody())
        val result = web.getSalesItemBySubjectId(1)
        Truth.assertThat(result.getOrNull()?.productId).isEqualTo(4399)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getSalesItemBySubjectId_failure() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getProductByGraphQL(any(), any())
        } returns Response.error(400, "".toResponseBody())
        val result = web.getSalesItemBySubjectId(1)
        Truth.assertThat(result.getOrNull()?.productId).isEqualTo(null)
        Truth.assertThat(result.isSuccess).isFalse()
    }
}
