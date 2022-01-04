package com.cmoney.backend2.data.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.data.service.api.FundIdWithError
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class DataWebImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: DataService
    private lateinit var setting: Setting
    private lateinit var dataWeb: DataWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        dataWeb = DataWebImpl(
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `getFundIdData 服務成功`() = mainCoroutineRule.runBlockingTest {
        val response = FundIdWithError(
            state = FundIdWithError.STATE_SUCCESS,
            title = listOf("日期"),
            data = listOf(listOf("20211122"))
        )

        coEvery {
            service.getFundIdData(
                authToken = any(),
                url = any()
            )
        } returns Response.success(response)

        val result = dataWeb.getFundIdData(fundId = 190, params = "1")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.title?.size).isEqualTo(1)
    }

    @Test
    fun `getFundIdData 服務空資料`() = mainCoroutineRule.runBlockingTest {
        val response = FundIdWithError(
            state = FundIdWithError.STATE_EMPTY,
            title = listOf(),
            data = listOf(listOf())
        )

        coEvery {
            service.getFundIdData(
                authToken = any(),
                url = any()
            )
        } returns Response.success(response)

        val result = dataWeb.getFundIdData(fundId = 190, params = "1")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.title?.size).isEqualTo(0)
    }

    @Test
    fun `getFundIdData 服務失敗`() = mainCoroutineRule.runBlockingTest {
        val response = FundIdWithError(
            state = FundIdWithError.STATE_FAIL,
            title = listOf(),
            data = listOf(listOf())
        )

        coEvery {
            service.getFundIdData(
                authToken = any(),
                url = any()
            )
        } returns Response.success(response)

        val result = dataWeb.getFundIdData(fundId = 190, params = "1")
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
    }

    @Test
    fun `getFundIdData 服務逾時`() = mainCoroutineRule.runBlockingTest {
        val response = FundIdWithError(
            state = FundIdWithError.STATE_TIMEOUT,
            title = listOf(),
            data = listOf(listOf())
        )

        coEvery {
            service.getFundIdData(
                authToken = any(),
                url = any()
            )
        } returns Response.success(response)

        val result = dataWeb.getFundIdData(fundId = 190, params = "1")
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
    }

}
