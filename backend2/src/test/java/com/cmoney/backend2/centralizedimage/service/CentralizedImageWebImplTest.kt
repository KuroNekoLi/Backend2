package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.centralizedimage.service.api.upload.GenreAndSubGenre
import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CentralizedImageWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

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
            dispatcher = TestDispatcherProvider()
        )
    }

    @Test
    fun `upload_成功`() = testScope.runTest {
        coEvery {
            service.upload(
                authorization = any(),
                genre = any(),
                subGenre = any(),
                file = any()
            )
        } returns Response.success(UploadResponseBody("publicImageUrl"))
        val result =
            webImpl.upload(
                GenreAndSubGenre.ServiceTestSwagger,
                getTestFile("src/test/resources/image/maple-leaf-1510431-639x761.jpeg")
            )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.url).isEqualTo("publicImageUrl")
    }

    @Test
    fun `upload_檔案太大失敗`() = testScope.runTest {
        coEvery {
            service.upload(
                authorization = any(),
                genre = any(),
                subGenre = any(),
                file = any()
            )
        } returns Response.success(UploadResponseBody("publicImageUrl"))
        val file = getTestFile("src/test/resources/image/maple-leaf-1510431-1279x1523.jpeg")
        val result = webImpl.upload(GenreAndSubGenre.ServiceTestSwagger, file)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(IllegalArgumentException::class.java)
        Truth.assertThat(exception?.message).isEqualTo("圖片大小限制1MB")
    }

    @Test
    fun `upload_401_UNAUTHORIZATION`() = testScope.runTest {
        coEvery {
            service.upload(
                authorization = any(),
                genre = any(),
                subGenre = any(),
                file = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val file = getTestFile("src/test/resources/image/maple-leaf-1510431-639x761.jpeg")
        val result = webImpl.upload(GenreAndSubGenre.ServiceTestSwagger, file)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
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