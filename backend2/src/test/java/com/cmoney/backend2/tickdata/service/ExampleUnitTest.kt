package com.cmoney.backend2.tickdata.service

import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class ExampleUnitTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: TickDataService
    private lateinit var tickDataWeb: TickDataWeb
    private lateinit var apiParam: MemberApiParam
    private val gson = GsonBuilder().serializeNulls().create()

    @Before
    fun setUp() {
        apiParam = MemberApiParam(99, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        MockKAnnotations.init(this)
        tickDataWeb = TickDataWebImpl(gson, TestSetting(), service, TestDispatcherProvider())
    }

    @Test
    fun emptyTest() {
    }
}