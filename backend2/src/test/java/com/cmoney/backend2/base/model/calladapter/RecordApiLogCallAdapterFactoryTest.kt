package com.cmoney.backend2.base.model.calladapter

import com.cmoney.backend2.base.TestSetting
import com.cmoney.backend2.testing.noContentMockResponse
import com.cmoney.core.CoroutineTestRule
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.common.truth.Truth
import com.google.gson.reflect.TypeToken
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import okhttp3.mockwebserver.MockWebServer
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


@RunWith(RobolectricTestRunner::class)
class RecordApiLogCallAdapterFactoryTest {
    interface TestService {
        @RecordApi
        @GET(value = "Request")
        suspend fun method(): Response<Void>
    }

    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @get:Rule
    val mockServer = MockWebServer()

    private val RECORD_API_ANNOTATIONS = arrayOfNulls<Annotation>(1).apply {
        this[0] = TestService::method.annotations.first()
    }
    private val NO_ANNOTATION = arrayOfNulls<Annotation>(0)
    private val setting = TestSetting()
    private val logDataRecorder = mockk<LogDataRecorder>(relaxed = true)
    private val factory: CallAdapter.Factory =
        RecordApiLogCallAdapterFactory(setting = setting, logDataRecorder = logDataRecorder)
    private val retrofit = Retrofit.Builder().baseUrl(mockServer.url("/"))
        .addCallAdapterFactory(factory).build()

    @Test
    fun rawTypeThrows() {
        try {
            factory.get(Call::class.java, RECORD_API_ANNOTATIONS, retrofit)
            error("")
        } catch (e: IllegalArgumentException) {
            Truth.assertThat(e.message)
                .isEqualTo("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>")
        }
    }

    @Test
    fun responseType() {
        val classType: Type = object : TypeToken<Call<String>>() {}.type
        Truth.assertThat(factory[classType, RECORD_API_ANNOTATIONS, retrofit]!!.responseType())
            .isEqualTo(String::class.java)
        val wilcardType: Type = object : TypeToken<Call<out String?>?>() {}.type
        Truth.assertThat(factory[wilcardType, RECORD_API_ANNOTATIONS, retrofit]!!.responseType())
            .isEqualTo(String::class.java)
        val genericType: Type = object : TypeToken<Call<List<String?>?>?>() {}.type
        Truth.assertThat(factory[genericType, RECORD_API_ANNOTATIONS, retrofit]!!.responseType())
            .isEqualTo(object : TypeToken<List<String?>?>() {}.type)
    }

    @Test
    fun expectNullForNoRecordApiAnnotation() {
        Truth.assertThat(factory.get(Call::class.java, NO_ANNOTATION, retrofit)).isNull()
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