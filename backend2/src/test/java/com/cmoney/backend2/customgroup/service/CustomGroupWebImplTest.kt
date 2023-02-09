package com.cmoney.backend2.customgroup.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.customgroup.TW_CUSTOM_GROUP_CONTENT
import com.cmoney.backend2.customgroup.TW_CUSTOM_GROUP_LIST
import com.cmoney.backend2.customgroup.TW_CUSTOM_GROUP_LIST_WITH_CONTENT
import com.cmoney.backend2.customgroup.US_CUSTOM_GROUP_CONTENT
import com.cmoney.backend2.customgroup.US_CUSTOM_GROUP_LIST
import com.cmoney.backend2.customgroup.service.api.addcustomgroup.NewCustomGroupWithError
import com.cmoney.backend2.customgroup.service.api.common.CustomGroupType
import com.cmoney.backend2.customgroup.service.api.deletecustomgroup.DeleteCustomGroupCompleteWithError
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder.CustomGroupWithOrderWithError
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist.CustomGroupWithOrderAndListWithError
import com.cmoney.backend2.customgroup.service.api.getcustomlist.CustomListWithError
import com.cmoney.backend2.customgroup.service.api.searchstocks.SearchStocksResponseBody
import com.cmoney.backend2.customgroup.service.api.updatecustomgroupname.UpdateCustomGroupNameCompleteWithError
import com.cmoney.backend2.customgroup.service.api.updatecustomgrouporder.UpdateCustomGroupOrderCompleteWithError
import com.cmoney.backend2.customgroup.service.api.updatecustomlist.UpdateCustomListCompleteWithError
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CustomGroupWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    lateinit var service: CustomGroupService

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var customGroupWeb: CustomGroupWebImpl
    private lateinit var setting: Setting

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        customGroupWeb = CustomGroupWebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcher = TestDispatcherProvider()
        )
    }

    private inline fun <reified T> String.asCustomGroup(gson: Gson) =
        gson.fromJson<T>(this, object : TypeToken<T>() {}.type)

    @Test
    fun `getCustomGroupWithOrder_TW成功`() = testScope.runTest {
        val response = context.assets.open(TW_CUSTOM_GROUP_LIST)
            .bufferedReader()
            .use {
                it.readText()
            }
            .asCustomGroup<CustomGroupWithOrderWithError>(gson)

        coEvery {
            service.getCustomGroupWithOrder(any(), any(), any(), any(), any())
        } returns Response.success(response)
        val result =
            customGroupWeb.getCustomGroupListIncludeOrder(groupType = CustomGroupType.Stock)
        coVerify {
            service.getCustomGroupWithOrder(
                any(),
                any(),
                any(),
                any(),
                docType = CustomGroupType.Stock.name
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotEmpty()
    }

    @Test
    fun `getCustomGroupWithOrder_US成功`() = testScope.runTest {
        val response = context.assets.open(US_CUSTOM_GROUP_LIST)
            .bufferedReader()
            .use {
                it.readText()
            }
            .asCustomGroup<CustomGroupWithOrderWithError>(gson)

        coEvery {
            service.getCustomGroupWithOrder(any(), any(), any(), any(), any())
        } returns Response.success(response)
        val result =
            customGroupWeb.getCustomGroupListIncludeOrder(groupType = CustomGroupType.UsStock)
        coVerify {
            service.getCustomGroupWithOrder(
                any(),
                any(),
                any(),
                any(),
                docType = CustomGroupType.UsStock.name
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getCustomGroupWithOrder_失敗`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, CustomGroupWithOrderWithError::class.java)
        coEvery {
            service.getCustomGroupWithOrder(any(), any(), any(), any(), any())
        } returns Response.success(responseBody)
        val result = customGroupWeb.getCustomGroupListIncludeOrder()
        result.getOrThrow()
    }

    @Test
    fun `getCustomGroupContent_TW成功`() = testScope.runTest {
        val response = context.assets.open(TW_CUSTOM_GROUP_CONTENT)
            .bufferedReader()
            .use {
                it.readText()
            }
            .asCustomGroup<CustomListWithError>(gson)

        coEvery {
            service.getCustomGroupContent(any(), any(), any(), any(), any())
        } returns Response.success(response)
        val result = customGroupWeb.getCustomGroupContent(1234)
        coVerify {
            service.getCustomGroupContent(any(), any(), any(), any(), docNo = 1234)
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotEmpty()
    }

    @Test
    fun `getCustomGroupContent_US成功`() = testScope.runTest {
        val response = context.assets.open(US_CUSTOM_GROUP_CONTENT)
            .bufferedReader()
            .use {
                it.readText()
            }
            .asCustomGroup<CustomListWithError>(gson)

        coEvery {
            service.getCustomGroupContent(any(), any(), any(), any(), any())
        } returns Response.success(response)
        val result = customGroupWeb.getCustomGroupContent(1234)
        coVerify {
            service.getCustomGroupContent(any(), any(), any(), any(), docNo = 1234)
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getCustomGroupContent_失敗`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, CustomListWithError::class.java)
        coEvery {
            service.getCustomGroupContent(any(), any(), any(), any(), any())
        } returns Response.success(responseBody)
        val result = customGroupWeb.getCustomGroupContent(1234)
        result.getOrThrow()
    }

    @Test
    fun `getCustomGroupListIncludeOrderAndContent_TW成功`() = testScope.runTest {
        val response = context.assets.open(TW_CUSTOM_GROUP_LIST_WITH_CONTENT)
            .bufferedReader()
            .use {
                it.readText()
            }
            .asCustomGroup<CustomGroupWithOrderAndListWithError>(gson)
        coEvery {
            service.getCustomGroupWithOrderAndList(any(), any(), any(), any(), any())
        } returns Response.success(response)
        val result =
            customGroupWeb.getCustomGroupListIncludeOrderAndContent(groupType = CustomGroupType.Stock)
        coVerify {
            service.getCustomGroupWithOrderAndList(
                any(),
                any(),
                any(),
                any(),
                docType = CustomGroupType.Stock.name
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.group).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getCustomGroupListIncludeOrderAndContent_失敗`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, CustomGroupWithOrderAndListWithError::class.java)
        coEvery {
            service.getCustomGroupWithOrderAndList(any(), any(), any(), any(), any())
        } returns Response.success(responseBody)
        val result = customGroupWeb.getCustomGroupListIncludeOrderAndContent()
        result.getOrThrow()
    }

    @Test
    fun `updateCustomList_成功`() = testScope.runTest {
        val responseBody = UpdateCustomListCompleteWithError(isSuccess = true)
        coEvery {
            service.updateCustomList(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                docNo = any(),
                docName = any(),
                list = any()
            )
        } returns Response.success(responseBody)
        val result =
            customGroupWeb.updateCustomGroupNameAndContent(
                groupType = CustomGroupType.Stock,
                docNo = 1,
                docName = "",
                list = listOf()
            )
        coVerify {
            service.updateCustomList(
                guid = any(),
                authorization = any(),
                appId = any(),
                docNo = any(),
                docName = any(),
                list = any(),
                docType = CustomGroupType.Stock.name
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isTrue()
    }


    @Test(expected = ServerException::class)
    fun `updateCustomList_失敗`() = testScope.runTest {
        //GIVEN
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()

        val responseBody =
            gson.fromJson(responseBodyJson, UpdateCustomListCompleteWithError::class.java)

        coEvery {
            service.updateCustomList(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                docNo = any(),
                docName = any(),
                list = any()
            )
        } returns Response.success(responseBody)
        val result = customGroupWeb.updateCustomGroupNameAndContent(
            docNo = 1,
            docName = "",
            list = listOf()
        )
        result.getOrThrow()
    }

    @Test
    fun `addCustomGroup_成功`() = testScope.runTest {
        val responseBody = NewCustomGroupWithError(docNo = 1, docName = "DocName")

        coEvery {
            service.addCustomGroup(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                docName = any()
            )
        } returns Response.success(responseBody)

        val result = customGroupWeb.addCustomGroup(groupType = CustomGroupType.Stock, docName = "")
        coVerify {
            service.addCustomGroup(
                guid = any(),
                authorization = any(),
                appId = any(),
                docName = any(),
                docType = CustomGroupType.Stock.name
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.docName).isEqualTo("DocName")
    }

    @Test(expected = ServerException::class)
    fun `addCustomGroup_失敗`() = testScope.runTest {
        //GIVEN
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()

        val responseBody =
            gson.fromJson(responseBodyJson, NewCustomGroupWithError::class.java)
        coEvery {
            service.addCustomGroup(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                docName = any()
            )
        } returns Response.success(responseBody)
        val result = customGroupWeb.addCustomGroup(docName = "")
        result.getOrThrow()
    }

    @Test
    fun `deleteCustomGroup_成功`() = testScope.runTest {
        //GIVEN
        val responseBody = DeleteCustomGroupCompleteWithError(isSuccess = true)

        coEvery {
            service.deleteCustomGroup(
                guid = any(),
                authorization = any(),
                appId = any(),
                docNo = any()
            )
        } returns Response.success(responseBody)

        //WHEN
        val result = customGroupWeb.deleteCustomGroup(1)

        //THEN
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isTrue()
    }

    @Test
    fun `deleteCustomGroup_失敗`() = testScope.runTest {
        //GIVEN
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()

        val responseBody =
            gson.fromJson(responseBodyJson, DeleteCustomGroupCompleteWithError::class.java)
        coEvery {
            service.deleteCustomGroup(
                guid = any(),
                authorization = any(),
                appId = any(),
                docNo = any()
            )
        } returns Response.success(responseBody)

        //WHEN
        val result = customGroupWeb.deleteCustomGroup(1)
        //THEN
        checkServerException(result = result)
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun `updateCustomGroupOrder_成功`() = testScope.runTest {
        val responseBody = UpdateCustomGroupOrderCompleteWithError(isSuccess = true)

        coEvery {
            service.updateCustomGroupOrder(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                orderMap = any()
            )
        } returns Response.success(responseBody)
        val result = customGroupWeb.updateCustomGroupOrder(
            groupType = CustomGroupType.Stock,
            docNoList = listOf()
        )
        coVerify {
            service.updateCustomGroupOrder(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = CustomGroupType.Stock.name,
                orderMap = any()
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun `updateCustomGroupOrder_失敗`() = testScope.runTest {
        //GIVEN
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()

        val responseBody =
            gson.fromJson(responseBodyJson, UpdateCustomGroupOrderCompleteWithError::class.java)

        coEvery {
            service.updateCustomGroupOrder(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                orderMap = any()
            )
        } returns Response.success(responseBody)
        val result = customGroupWeb.updateCustomGroupOrder(docNoList = listOf())
        result.getOrThrow()
    }

    @Test
    fun `renameCustomGroup_成功`() = testScope.runTest {
        val responseBody = UpdateCustomGroupNameCompleteWithError(isSuccess = true)

        coEvery {
            service.updateCustomGroupName(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                docNo = any(),
                docName = any()
            )
        } returns Response.success(responseBody)
        val result = customGroupWeb.renameCustomGroup(
            groupType = CustomGroupType.Stock,
            docNo = 1,
            newDocName = ""
        )
        coVerify {
            service.updateCustomGroupName(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = CustomGroupType.Stock.name,
                docNo = any(),
                docName = any()
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isTrue()
    }

    @Test(expected = ServerException::class)
    fun `renameCustomGroup_失敗`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()

        val responseBody =
            gson.fromJson(responseBodyJson, UpdateCustomGroupNameCompleteWithError::class.java)

        coEvery {
            service.updateCustomGroupName(
                guid = any(),
                authorization = any(),
                appId = any(),
                docType = any(),
                docNo = any(),
                docName = any()
            )
        } returns Response.success(responseBody)


        val result = customGroupWeb.renameCustomGroup(docNo = 1, newDocName = "")
        result.getOrThrow()
    }

    @Test
    fun `searchStocks搜尋關鍵字找股票 成功`() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = SearchStocksResponseBody()
        //設定api成功時的回傳
        coEvery {
            service.searchStocks(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = customGroupWeb.searchStocks("A")
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `searchStocks搜尋關鍵字找股票 失敗 發生ServerException`() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        //設定api成功時的回傳
        coEvery {
            service.searchStocks(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = customGroupWeb.searchStocks("A")
        checkServerException(result)
    }

    private fun <T> checkServerException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }
}