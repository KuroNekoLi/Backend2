package com.cmoney.backend2.virtualtrading2.web

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.virtualtrading2.getCreateAccountTypeIsStockSuccess
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountRequest
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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
class VirtualTrading2WebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    private val context = ApplicationProvider.getApplicationContext<Context>()
    @MockK
    private lateinit var service: VirtualTrading2Service
    private lateinit var web: VirtualTrading2Web
    private val gson = GsonBuilder().setLenient().setPrettyPrinting().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = VirtualTrading2WebImpl(
            dispatcher = TestDispatcherProvider(),
            gson = gson,
            requestConfig = object : VirtualTradingRequestConfig {
                override fun getDomain(): String {
                    return ""
                }

                override fun getBearerToken(): String {
                    return ""
                }
            },
            service = service
        )
    }

    @Test
    fun `createAccount_AccountInvestType是Stock_傳送Server時AccountInvestType是1`() = testScope.runTest {
        val responseBody = context.getCreateAccountTypeIsStockSuccess()

        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
            )
        )
        coVerify {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = CreateAccountRequestBody(
                    accountInvestType = 1,
                    cardSn = 0
                )
            )
        }
    }

    @Test
    fun `createAccount_AccountInvestType是Options_傳送Server時AccountInvestType是2`() = testScope.runTest {
        val responseBody = context.getCreateAccountTypeIsStockSuccess()

        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Options
            )
        )
        coVerify {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = CreateAccountRequestBody(
                    accountInvestType = 2,
                    cardSn = 0
                )
            )
        }
    }
}