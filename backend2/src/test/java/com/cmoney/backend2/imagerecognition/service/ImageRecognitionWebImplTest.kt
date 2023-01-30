package com.cmoney.backend2.imagerecognition.service

import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class ImageRecognitionWebImplTest {
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: ImageRecognitionService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting
    private lateinit var web: ImageRecognitionWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        web = ImageRecognitionWebImpl(
            baseHost = "",
            setting = setting,
            service = service,
            gson = gson,
            dispatcherProvider = TestDispatcherProvider()
        )
    }

    @Test
    fun getPictureWords_success():Unit = testScope.runTest {
        val responseBody = PictureWordsResponseBody(
            result = "9988"
        )
        coEvery {
            service.getPictureWords(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getPictureWords(
            photoBytes = byteArrayOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrNull()
        Truth.assertThat(data).isNotNull()
        requireNotNull(data)
        Truth.assertThat(data.result).isEqualTo("9988")
    }

    @Test
    fun getPictureWords_401_UNAUTHORIZATION(): Unit = testScope.runTest {
        coEvery {
            service.getPictureWords(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = web.getPictureWords(
            photoBytes = byteArrayOf()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }
}