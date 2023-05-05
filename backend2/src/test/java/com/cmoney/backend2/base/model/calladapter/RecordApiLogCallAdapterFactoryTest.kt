package com.cmoney.backend2.base.model.calladapter

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.testing.noContentMockResponse
import com.cmoney.core.CoroutineTestRule
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.common.truth.Truth
import com.google.gson.reflect.TypeToken
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import java.lang.reflect.Type

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class RecordApiLogCallAdapterFactoryTest {

    private val testScope = TestScope()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @get:Rule
    val mockServer = MockWebServer()
    private val recordApiAnnotation = arrayOfNulls<Annotation>(1).apply {
        this[0] = TestService::method.annotations.first()
    }
    private val noAnnotation = arrayOfNulls<Annotation>(0)
    private val logDataRecorder = mockk<LogDataRecorder>(relaxed = true)
    private val manager = mockk<GlobalBackend2Manager>(relaxed = true)
    private lateinit var factory: CallAdapter.Factory
    private lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        MockKAnnotations.init()
        factory = RecordApiLogCallAdapterFactory(manager, logDataRecorder = logDataRecorder)
        retrofit = Retrofit.Builder().baseUrl(mockServer.url("/"))
            .addCallAdapterFactory(factory).build()
    }

    @Test
    fun rawTypeThrows() {
        try {
            factory.get(Call::class.java, recordApiAnnotation, retrofit)
            error("")
        } catch (e: IllegalArgumentException) {
            Truth.assertThat(e.message)
                .isEqualTo("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>")
        }
    }

    @Test
    fun responseType() {
        val classType: Type = object : TypeToken<Call<String>>() {}.type
        Truth.assertThat(factory[classType, recordApiAnnotation, retrofit]!!.responseType())
            .isEqualTo(String::class.java)
        val wilcardType: Type = object : TypeToken<Call<out String?>?>() {}.type
        Truth.assertThat(factory[wilcardType, recordApiAnnotation, retrofit]!!.responseType())
            .isEqualTo(String::class.java)
        val genericType: Type = object : TypeToken<Call<List<String?>?>?>() {}.type
        Truth.assertThat(factory[genericType, recordApiAnnotation, retrofit]!!.responseType())
            .isEqualTo(object : TypeToken<List<String?>?>() {}.type)
    }

    @Test
    fun expectNullForNoRecordApiAnnotation() {
        Truth.assertThat(factory.get(Call::class.java, noAnnotation, retrofit)).isNull()
    }

    @Test
    fun executeWillInvokeLogApi() = runBlocking {
        mockServer.enqueue(noContentMockResponse())
        val service = retrofit.create(TestService::class.java)
        service.method()
        verify(exactly = 1) {
            logDataRecorder.logApi(apiLog = any())
        }
    }
}

interface TestService {
    @RecordApi
    @GET(value = "Request")
    suspend fun method(): Response<Void>
}