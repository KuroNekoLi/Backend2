package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.centralizedimage.service.api.upload.GenreAndSubGenre
import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
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
import java.io.*

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

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = CentralizedImageWebImpl(
            service = service,
            gson = gson,
            manager = manager,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getCentralizedImageSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getVerifyCode_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}centralizedImage/v1/upload/servicetest/swagger"
        val urlSlot = slot<String>()
        coEvery {
            service.upload(
                url = capture(urlSlot),
                authorization = any(),
                file = any()
            )
        } returns Response.success(UploadResponseBody("publicImageUrl"))
        webImpl.upload(
            GenreAndSubGenre.ServiceTestSwagger,
            getTestFile("src/test/resources/image/maple-leaf-1510431-639x761.jpeg")
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }


    @Test
    fun upload_success() = testScope.runTest {
        coEvery {
            service.upload(
                url = any(),
                authorization = any(),
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
    fun `upload_file is too big_IllegalArgumentException`() = testScope.runTest {
        coEvery {
            service.upload(
                url = any(),
                authorization = any(),
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
    fun upload_401_UNAUTHORIZATION() = testScope.runTest {
        coEvery {
            service.upload(
                url = any(),
                authorization = any(),
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