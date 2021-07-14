package com.cmoney.backend2.tickdata.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: TickDataService
    private lateinit var tickDataWeb: TickDataWeb
    private lateinit var apiParam: MemberApiParam
    private val gson = GsonBuilder().serializeNulls().create()

    @Before
    fun setUp() {
        apiParam = MemberApiParam(99, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        MockKAnnotations.init(this)
        tickDataWeb = TickDataWebImpl(gson, TestSetting(), service, TestDispatcher())
    }

    @Test
    fun emptyTest() {
    }
}