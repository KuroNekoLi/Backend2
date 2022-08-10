package com.cmoney.backend2.videochannel.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class VideoChannelWebImplTest {
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var videoChannelService: VideoChannelService
    private lateinit var videoChannelWeb: VideoChannelWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        videoChannelWeb = VideoChannelWebImpl(
            service = videoChannelService,
            gson = gson,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun getYoutubeVideos_success() = mainCoroutineRule.runTest {
        coEvery {
            videoChannelService.getYoutubeVideos(
                id = any(),
                amount = any(),
                time = any()
            )
        } returns Response.success("[]".toResponseBody())

        val result = videoChannelWeb.getYoutubeVideos(
            youtubeChannelId = "",
            amount = 0,
            time = 0
        )

        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getYoutubeVideos_failure_status_code() = mainCoroutineRule.runTest {
        coEvery {
            videoChannelService.getYoutubeVideos(
                id = any(),
                amount = any(),
                time = any()
            )
        } returns Response.error(
            400,
            // language=JSON
            """
              {
                "Error": {
                  "Code": 1,
                  "Message": "參數錯誤",
                  "InnerMessage": "channel id 不存在"
                }
              }
            """.toResponseBody()
        )

        val result = videoChannelWeb.getYoutubeVideos(
            youtubeChannelId = "",
            amount = 0,
            time = 0
        )

        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun getYoutubeVideos_failure_error_object() = mainCoroutineRule.runTest {
        coEvery {
            videoChannelService.getYoutubeVideos(
                id = any(),
                amount = any(),
                time = any()
            )
        } returns Response.success(
            // language=JSON
            """
              {
                "Error": {
                  "Code": 1,
                  "Message": "參數錯誤",
                  "InnerMessage": "channel id 不存在"
                }
              }
            """.toResponseBody()
        )

        val result = videoChannelWeb.getYoutubeVideos(
            youtubeChannelId = "",
            amount = 0,
            time = 0
        )

        Truth.assertThat(result.isSuccess).isFalse()
    }
}