package com.cmoney.backend2.virtualtrading2.web

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Web
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2WebImpl
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

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
}