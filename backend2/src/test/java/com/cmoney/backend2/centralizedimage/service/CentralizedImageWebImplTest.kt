package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
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
import java.io.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CentralizedImageWebImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: CentralizedImageService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var webImpl: CentralizedImageWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = CentralizedImageWebImpl(
                service = service,
                setting = TestSetting(),
                jsonParser = gson,
                dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `upload_成功`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.upload(
                    authorization = any(),
                    destination = any(),
                    file = any()
            )
        } returns Response.success(UploadResponseBody("publicImageUrl"))
        val result = webImpl.upload(
            CentralizedImageWeb.Destination.SERVICETEST_SWAGGER,
            getTestFile("src/test/resources/image/maple-leaf-1510431-639x761.jpeg")
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.url).isEqualTo("publicImageUrl")
    }

    @Test
    fun `upload_檔案太大失敗`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.upload(
                    authorization = any(),
                    destination = any(),
                    file = any()
            )
        } returns Response.success(UploadResponseBody("publicImageUrl"))
        val file = getTestFile("src/test/resources/image/maple-leaf-1510431-1279x1523.jpeg")
        val exception = webImpl.upload(
            CentralizedImageWeb.Destination.SERVICETEST_SWAGGER,
            file
        ).exceptionOrNull()
        Truth.assertThat(exception?.message).isEqualTo("圖片大小限制1MB")
    }

    private fun <T> checkServerException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    private fun getTestFile(
            srcPath: String,
            outputPath: String = "src/test/resources/image/targetFile.tmp"
    ): File {
        val initialStream: InputStream = FileInputStream(File(srcPath))
        val buffer = ByteArray(initialStream.available())
        initialStream.read(buffer)
        initialStream.close()

        val targetFile = File(outputPath)
        val outStream: OutputStream = FileOutputStream(targetFile)
        outStream.write(buffer)
        outStream.close()

        return targetFile
    }
}