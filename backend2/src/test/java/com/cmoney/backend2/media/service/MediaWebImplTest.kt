package com.cmoney.backend2.media.service

import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.media.service.api.getmediadetail.GetMediaDetailResponseWithError
import com.cmoney.backend2.media.service.api.getmediainfo.MediaInfoWithError
import com.cmoney.backend2.media.service.api.getmediaurl.GetMediaUrlResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class MediaWebImplTest {
    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private val mediaService = mockk<MediaService>()
    private val gson = Gson()
    private lateinit var mediaWeb: MediaWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mediaWeb = MediaWebImpl(TestSetting(), mediaService, gson, TestDispatcherProvider())
    }

    @Test
    fun getMediaList_success() = testScope.runTest {
        coEvery {
            mediaService.getMediaList(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any(),
                chargeType = any(),
                tagIdList = any()
            )
        } returns Response.success("[]".toResponseBody())

        val result = mediaWeb.getMediaList(
            skipCount = 0,
            fetchCount = 0,
            chargeType = 0,
            tagIdList = listOf(1)
        )

        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getMediaList_failure_status_code() = testScope.runTest {
        coEvery {
            mediaService.getMediaList(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any(),
                chargeType = any(),
                tagIdList = any()
            )
        } returns Response.error(400, "".toResponseBody())

        val result = mediaWeb.getMediaList(
            skipCount = 0,
            fetchCount = 0,
            chargeType = 0,
            tagIdList = listOf(1)
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getMediaList_failure_error_object() = testScope.runTest {
        coEvery {
            mediaService.getMediaList(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any(),
                chargeType = any(),
                tagIdList = any()
            )
        } returns Response.success(
            // language=JSON
            """
              {
                "Error": {
                  "Code": 101,
                  "Message": "Auth Failed"
                }
              }
            """.toResponseBody()
        )

        val result = mediaWeb.getMediaList(
            skipCount = 0,
            fetchCount = 0,
            chargeType = 0,
            tagIdList = listOf(1)
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getMediaPurchaseUrl_success() = testScope.runTest {
        coEvery {
            mediaService.getMediaPurchaseUrl(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                mediaId = any()
            )
        } returns Response.success(GetMediaUrlResponseBody(""))
        val result = mediaWeb.getMediaPurchaseUrl(
            mediaId = 0L
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getMediaPurchaseUrl_failure_status_code() = testScope.runTest {
        coEvery {
            mediaService.getMediaPurchaseUrl(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                mediaId = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = mediaWeb.getMediaPurchaseUrl(
            mediaId = 0L
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getMediaUrl_success() = testScope.runTest {
        coEvery {
            mediaService.getMediaUrl(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                mediaId = any()
            )
        } returns Response.success(GetMediaUrlResponseBody(""))
        val result = mediaWeb.getMediaUrl(
            mediaId = 0L
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getMediaUrl_failure_status_code() = testScope.runTest {
        coEvery {
            mediaService.getMediaUrl(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                mediaId = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = mediaWeb.getMediaUrl(
            mediaId = 0L
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getLiveStreamList_success() = testScope.runTest {
        coEvery {
            mediaService.getLiveStreamList(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any(),
                chargeType = any()
            )
        } returns Response.success("[]".toResponseBody())
        val result = mediaWeb.getLiveStreamList(
            0,
            0,
            0
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getLiveStreamList_failure_status_code() = testScope.runTest {
        coEvery {
            mediaService.getLiveStreamList(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any(),
                chargeType = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = mediaWeb.getLiveStreamList(
            0,
            0,
            0
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getLiveStreamList_failure_error_object() = testScope.runTest {
        coEvery {
            mediaService.getLiveStreamList(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any(),
                chargeType = any()
            )
        } returns Response.success(
            // language=JSON
            """
                {
                  "Error": {
                    "Code": 101,
                    "Message": "Auth Failed"
                  }
                }
            """.trimIndent()
                .toResponseBody()
        )
        val result = mediaWeb.getLiveStreamList(
            0,
            0,
            0
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getPaidMediaListOfMember_success() = testScope.runTest {
        coEvery {
            mediaService.getPaidMediaListOfMember(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success("[]".toResponseBody())
        val result = mediaWeb.getPaidMediaListOfMember(0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getPaidMediaListOfMember_failure_status_code() = testScope.runTest {
        coEvery {
            mediaService.getPaidMediaListOfMember(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = mediaWeb.getPaidMediaListOfMember(0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun getPaidMediaListOfMember_failure_error_object() = testScope.runTest {
        val responseBody =
            """{"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}""".toResponseBody()
        coEvery {
            mediaService.getPaidMediaListOfMember(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(responseBody)

        val result = mediaWeb.getPaidMediaListOfMember(0, 0)
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun getPaidMediaList_success() = testScope.runTest {
        coEvery {
            mediaService.getPaidMediaList(
                action = any(),
                appId = any(),
                authorization = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success("[]".toResponseBody())

        val result = mediaWeb.getPaidMediaList(0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getPaidMediaList_failure_status_code() = testScope.runTest {
        coEvery {
            mediaService.getPaidMediaList(
                action = any(),
                guid = any(),
                appId = any(),
                authorization = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.error(400, "".toResponseBody())

        val result = mediaWeb.getPaidMediaList(0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun getPaidMediaList_failure_error_object() = testScope.runTest {
        val responseBody =
            """{"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}""".toResponseBody()
        coEvery {
            mediaService.getPaidMediaList(
                action = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                fetchCount = any(),
                skipCount = any()
            )
        } returns Response.success(responseBody)

        val result = mediaWeb.getPaidMediaList(0, 0)
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun getPaidLiveList_success() = testScope.runTest {
        coEvery {
            mediaService.getPaidLiveList(
                action = any(),
                appId = any(),
                authorization = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success("[]".toResponseBody())
        val result = mediaWeb.getPaidLiveList(0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getPaidLiveList_failure_status_code() = testScope.runTest {
        coEvery {
            mediaService.getPaidLiveList(
                action = any(),
                appId = any(),
                authorization = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.error(400, "".toResponseBody())

        val result = mediaWeb.getPaidLiveList(0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun getPaidLiveList_failure_error_object() = testScope.runTest {
        val responseBody =
            """{"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}""".toResponseBody()
        coEvery {
            mediaService.getPaidLiveList(
                action = any(),
                appId = any(),
                authorization = any(),
                guid = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(responseBody)
        val result = mediaWeb.getPaidLiveList(0, 0)
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun getMediaInfo_success() {
        testScope.runTest {
            coEvery {
                mediaService.getMediaInfo(
                    action = any(),
                    mediaId = any(),
                    appId = any(),
                    authorization = any(),
                    guid = any()
                )
            } returns Response.success(MediaInfoWithError(1L))
            val result = mediaWeb.getMediaInfo(1)
            Truth.assertThat(result.isSuccess).isTrue()
            Truth.assertThat(result.getOrThrow().id).isEqualTo(1)
        }
    }

    @Test
    fun getMediaInfo_failure() {
        testScope.runTest {
            coEvery {
                mediaService.getMediaInfo(
                    action = any(),
                    mediaId = any(),
                    appId = any(),
                    authorization = any(),
                    guid = any()
                )
            } returns Response.success(
                Gson().fromJson(
                    // language=JSON
                    """
                {
                  "Error": {
                    "Code": 101,
                    "Message": "Auth Failed"
                  }
                }""".trimIndent(),
                    MediaInfoWithError::class.java
                )
            )
            val result = mediaWeb.getMediaInfo(1)
            Truth.assertThat(result.isFailure).isTrue()
            Truth.assertThat(result.exceptionOrNull()).isNotNull()
            Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Auth Failed")
        }
    }

    @Test
    fun getMediaDetail_success() {
        testScope.runTest {
            coEvery {
                mediaService.getMediaDetail(
                    action = any(),
                    mediaId = any(),
                    appId = any(),
                    authorization = any(),
                    guid = any(),
                    device = any()
                )
            } returns Response.success(
                GetMediaDetailResponseWithError(
                    1,
                    "",
                    "",
                    "",
                    "",
                    false,
                    ""
                )
            )
            val result = mediaWeb.getMediaDetail(1)
            Truth.assertThat(result.isSuccess).isTrue()
            Truth.assertThat(result.getOrThrow().mediaId).isEqualTo(1)
        }
    }

    @Test
    fun getMediaDetail_failure() {
        testScope.runTest {
            coEvery {
                mediaService.getMediaDetail(
                    action = any(),
                    mediaId = any(),
                    appId = any(),
                    authorization = any(),
                    guid = any(),
                    device = any()
                )
            } returns Response.success(
                Gson().fromJson(
                    // language=JSON
                    """
                {
                  "Error": {
                    "Code": 101,
                    "Message": "Auth Failed"
                  }
                }""".trimIndent(),
                    GetMediaDetailResponseWithError::class.java
                )
            )
            val result = mediaWeb.getMediaDetail(1)
            Truth.assertThat(result.isFailure).isTrue()
            Truth.assertThat(result.exceptionOrNull()).isNotNull()
            Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Auth Failed")
        }
    }

    @Test
    fun getPaidMediaListOfMemberByAppIdSuccess() {
        testScope.runTest {
            coEvery {
                mediaService.getPaidMediaListOfMemberByAppId(
                    action = any(),
                    appId = any(),
                    authorization = any(),
                    guid = any(),
                    skipCount = any(),
                    fetchCount = any()
                )
            } returns Response.success("[]".toResponseBody())
            val result = mediaWeb.getPaidMediaListOfMemberByAppId(1, 0)
            Truth.assertThat(result.isSuccess).isTrue()
            Truth.assertThat(result.getOrThrow().size).isEqualTo(0)
        }
    }

    @Test
    fun getPaidMediaListOfMemberByAppIdFailure() {
        testScope.runTest {
            val responseBody =
                """{"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}""".toResponseBody()
            coEvery {
                mediaService.getPaidMediaListOfMemberByAppId(
                    action = any(),
                    appId = any(),
                    authorization = any(),
                    guid = any(),
                    skipCount = any(),
                    fetchCount = any()
                )
            } returns Response.success(responseBody)
            val result = mediaWeb.getPaidMediaListOfMemberByAppId(0, 0)
            val exception = result.exceptionOrNull() as ServerException
            Truth.assertThat(result.isFailure).isTrue()
            Truth.assertThat(exception.code).isEqualTo(101)
        }
    }
}

